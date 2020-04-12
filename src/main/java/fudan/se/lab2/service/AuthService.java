package fudan.se.lab2.service;

import fudan.se.lab2.controller.request.ApplyConferenceRequest;
import fudan.se.lab2.controller.request.MessageRequest;
import fudan.se.lab2.controller.request.MyConferenceRequest;
import fudan.se.lab2.controller.request.RegisterRequest;
import fudan.se.lab2.controller.response.MessageResponse;
import fudan.se.lab2.controller.response.MyConferenceResponce;
import fudan.se.lab2.domain.Conference;
import fudan.se.lab2.domain.Invitation;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.exception.ConferHasBeenRegisteredException;
import fudan.se.lab2.exception.UNHasBeenRegisteredException;
import fudan.se.lab2.repository.AuthorityRepository;
import fudan.se.lab2.repository.ConferenceRepository;
import fudan.se.lab2.repository.InvitationRepository;
import fudan.se.lab2.repository.UserRepository;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author LBW
 */
@Service
public class AuthService {
    private UserRepository userRepository;
    private AuthorityRepository authorityRepository;
    private ConferenceRepository conferenceRepository;
    private InvitationRepository invitationRepository;
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public AuthService(UserRepository userRepository, AuthorityRepository authorityRepository,
    ConferenceRepository conferenceRepository,InvitationRepository invitationRepository,JwtTokenUtil jwtTokenUtil) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.conferenceRepository = conferenceRepository;
        this.invitationRepository = invitationRepository;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public User register(RegisterRequest request) throws UNHasBeenRegisteredException {
        if(userRepository.findByUsername(request.getUsername()) != null){
            throw new UNHasBeenRegisteredException(request.getUsername());
        }
        User user = new User(request.getUsername(),request.getPassword(),
                request.getFullname(),request.getEmail(),request.getArea(),request.getUnit());
        userRepository.save(user);
        return user;
    }

    //return user
    public User login(String username, String password) throws UsernameNotFoundException, BadCredentialsException {
        User user = userRepository.findByUsername(username);
        if (user == null)throw new UsernameNotFoundException(username);
        if (!password.equals(user.getPassword()))throw new BadCredentialsException(username);

        return user;
    }
    //return token
    public String login(String username) throws UsernameNotFoundException, BadCredentialsException {
        User user = userRepository.findByUsername(username);
        if (user == null)throw new UsernameNotFoundException(username);

        return jwtTokenUtil.generateToken(user);
    }


    public Conference applyConfer(String username,ApplyConferenceRequest request) throws ConferHasBeenRegisteredException {
        if(conferenceRepository.findByFullName(request.getFullName()) != null)
            throw new ConferHasBeenRegisteredException(request.getFullName());
        User user = userRepository.findByUsername(username);
        user.getConferenceFullname().add(request.getFullName());
        Conference conference = new Conference(request.getAbbr(),request.getFullName(),
                request.getHoldDate(),request.getHoldPlace(),request.getSubmissionDeadline(),
                request.getReleaseDate(),request.getUsername(),0);

        conferenceRepository.save(conference);
        return conference;
    }


    public Iterable<Conference> findAllConference(){
        Iterable<Conference> conferences =  conferenceRepository.findAll();
        return conferences;
    }


    public void findMyConference(String username,MyConferenceResponce responce){
        User user = userRepository.findByUsername(username);
        ArrayList<String> conferenceFullname = user.getConferenceFullname();
        for (String each:conferenceFullname){
            Conference conference = conferenceRepository.findByFullName(each);
            if (conference.getChair()==username)responce.getChairConference().add(conference);
            if (conference.getPCMembers().contains(username))responce.getPCConference().add(conference);
            if (conference.getAuthor().contains(username))responce.getAuthorConference().add(conference);
        }
    }

    public void getMessage(String username, MessageResponse response){
        ArrayList<Invitation> invitations = invitationRepository.findAllByInvitedParty(username);

        ArrayList<Conference> invitation = new ArrayList<>();
        ArrayList<Conference> application = conferenceRepository.findAllByChair(username);

        for (Invitation each: invitations){
            invitation.add(conferenceRepository.findByFullName(each.getConferenceFullname()));
        }

        response.setInvitation(invitation);
        response.setApplication(application);
    }

}
