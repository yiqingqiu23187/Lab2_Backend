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
public class AdminService {
    private UserRepository userRepository;
    private ConferenceRepository conferenceRepository;
    private InvitationRepository invitationRepository;
    private PaperRepository paperRepository;


    @Autowired
    public AdminService(UserRepository userRepository, AuthorityRepository authorityRepository,
                       ConferenceRepository conferenceRepository, InvitationRepository invitationRepository, PaperRepository paperRepository, JwtTokenUtil jwtTokenUtil) {
        this.userRepository = userRepository;
        this.conferenceRepository = conferenceRepository;
        this.invitationRepository = invitationRepository;
        this.paperRepository = paperRepository;

    }


    public void waitingConference(WaitingConferenceResponse response){
        ArrayList<Invitation> invitations = new ArrayList<>();
        Iterable<Invitation> temp      = invitationRepository.findByState(0);
        for (Invitation each:temp){
            invitations.add(each);
        }


        for (Invitation each:invitations) {
            Conference conference = conferenceRepository.findByFullName(each.getConferenceFullname());
            response.getWaitingConference().add(conference);
        }
    }

    public ArrayList<Conference> admin(){
        ArrayList<Conference> result = new ArrayList<>();
        Iterable<Conference> conferences =  conferenceRepository.findByState(0);

        for (Conference each:conferences){
            result.add(each);
        }
        System.out.println(result.size());
        return result;
    }

    public Conference handleConference(String conferenceFullname,Boolean agreeOrNot){
        Conference conference = conferenceRepository.findByFullName(conferenceFullname);
        if (agreeOrNot){
            conference.setState(1);
            conference.getPCMembers().add(conference.getChair());
        }
        else conference.setState(2);
        conferenceRepository.save(conference);
        return conference;
    }
}