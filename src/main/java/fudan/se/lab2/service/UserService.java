package fudan.se.lab2.service;

import fudan.se.lab2.controller.request.*;
import fudan.se.lab2.controller.response.*;
import fudan.se.lab2.domain.Conference;
import fudan.se.lab2.domain.Invitation;
import fudan.se.lab2.domain.Paper;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.exception.ConferHasBeenRegisteredException;
import fudan.se.lab2.exception.UNHasBeenRegisteredException;
import fudan.se.lab2.repository.*;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author LBW
 */
@Service
public class UserService {
    private UserRepository userRepository;
    private ConferenceRepository conferenceRepository;
    private InvitationRepository invitationRepository;
    private PaperRepository paperRepository;
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public UserService(UserRepository userRepository, AuthorityRepository authorityRepository,
                       ConferenceRepository conferenceRepository, InvitationRepository invitationRepository, PaperRepository paperRepository, JwtTokenUtil jwtTokenUtil) {
        this.userRepository = userRepository;
        this.conferenceRepository = conferenceRepository;
        this.invitationRepository = invitationRepository;
        this.paperRepository = paperRepository;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public User register(RegisterRequest request) throws UNHasBeenRegisteredException {
        if (userRepository.findByUsername(request.getUsername()) != null) {
            throw new UNHasBeenRegisteredException(request.getUsername());
        }
        User user = new User(request.getUsername(), request.getPassword(),
                request.getFullname(), request.getEmail(), request.getArea(), request.getJob());
        userRepository.save(user);
        return user;
    }

    //return user
    public User login(String username, String password) throws UsernameNotFoundException, BadCredentialsException {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException(username);
        if (!password.equals(user.getPassword())) throw new BadCredentialsException(username);

        return user;
    }

    //return token
    public String login(String username) throws UsernameNotFoundException, BadCredentialsException {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException(username);

        return jwtTokenUtil.generateToken(user);
    }


    public ArrayList<Conference> findAllConference() {
        ArrayList<Conference> result = new ArrayList<>();
        Iterable<Conference> conferences = conferenceRepository.findByState(1);
        for (Conference each : conferences) {
            result.add(each);
        }
        return result;
    }

    public void findMyConference(String username, MyConferenceResponce responce) {
        User user = userRepository.findByUsername(username);
        ArrayList<String> conferenceFullname = user.getConferenceFullname();
        for (String each : conferenceFullname) {
            Conference conference = conferenceRepository.findByFullName(each);
            if (conference.getChair().equals(username) && conference.getState() == 1)
                responce.getChairConference().add(conference);
            if (conference.getPCMembers().contains(username)) responce.getPCConference().add(conference);
            if (conference.getAuthor().contains(username)) responce.getAuthorConference().add(conference);
        }
    }

    //invitation I recieved and conference I applied
    public void getMessage(String username, MessageResponse response) {

        ArrayList<Invitation> invitations = new ArrayList<>();
        Iterable<Invitation> invitations1 = invitationRepository.findByInvitedParty(username);
        for (Invitation each : invitations1) {
            if (each.getState() == 0) invitations.add(each);
        }

        ArrayList<Conference> invitation = new ArrayList<>();

        for (Invitation each : invitations) {
            invitation.add(conferenceRepository.findByFullName(each.getConferenceFullname()));
        }

        Iterable<Conference> application = conferenceRepository.findByChair(username);
        ArrayList<Conference> applications = new ArrayList<>();
        for (Conference each : application) {
            applications.add(each);
        }
        response.setInvitation(invitation);
        response.setApplication(applications);
    }





}
