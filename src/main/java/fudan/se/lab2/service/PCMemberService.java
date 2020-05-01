package fudan.se.lab2.service;

import fudan.se.lab2.controller.request.*;
import fudan.se.lab2.controller.response.*;
import fudan.se.lab2.domain.*;
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
public class PCMemberService {
    private UserRepository userRepository;
    private ConferenceRepository conferenceRepository;
    private InvitationRepository invitationRepository;
    private PaperRepository paperRepository;
    private DistributionRepository distributionRepository;

    @Autowired
    public PCMemberService(UserRepository userRepository, AuthorityRepository authorityRepository,
                        ConferenceRepository conferenceRepository, InvitationRepository invitationRepository,
                           PaperRepository paperRepository,DistributionRepository distributionRepository) {
        this.userRepository = userRepository;
        this.conferenceRepository = conferenceRepository;
        this.invitationRepository = invitationRepository;
        this.paperRepository = paperRepository;
        this.distributionRepository = distributionRepository;
    }

    public Invitation handleInvitation(String username, String conferenceFullname,
                                       Boolean agreeOrNot,ArrayList<String> topics) {
        Invitation invitation = invitationRepository.
                findByInvitedPartyAndConferenceFullname(username, conferenceFullname);
        if (invitation != null) {
            if (agreeOrNot) {
                invitation.setState(1);
                invitationRepository.save(invitation);

                Conference conference = conferenceRepository.findByFullName(conferenceFullname);
                conference.getPCMembers().add(username);
                conferenceRepository.save(conference);

                User user = userRepository.findByUsername(username);
                user.getConferenceFullname().add(conferenceFullname);
                userRepository.save(user);

                Distribution distribution = new Distribution();
                distribution.setUserName(username);
                distribution.setConferenceFullname(conferenceFullname);
                distribution.setTopics(topics);
                distributionRepository.save(distribution);
            } else {
                invitation.setState(2);
                invitationRepository.save(invitation);
            }
        } else {
            System.out.println("this conference dose not exist");
        }
        return invitation;
    }
}