package fudan.se.lab2.service;

import fudan.se.lab2.domain.*;
import fudan.se.lab2.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    private MarkRepository markRepository;

    @Autowired
    public PCMemberService(UserRepository userRepository, MarkRepository markRepository,
                           ConferenceRepository conferenceRepository, InvitationRepository invitationRepository,
                           PaperRepository paperRepository, DistributionRepository distributionRepository) {
        this.userRepository = userRepository;
        this.markRepository = markRepository;
        this.conferenceRepository = conferenceRepository;
        this.invitationRepository = invitationRepository;
        this.paperRepository = paperRepository;
        this.distributionRepository = distributionRepository;
    }

    public Invitation handleInvitation(String username, String conferenceFullname,
                                       Boolean agreeOrNot, ArrayList<String> topics) {
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
                distribution.setUsername(username);
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


    public ArrayList<Paper> myDistribution(String username) {
        Iterable<Distribution> distributions = distributionRepository.findByUsername(username);
        ArrayList<Paper> papers = new ArrayList<>();
        for (Distribution e : distributions) {
            for (String each : e.getPaperTitles()) {
                Paper paper = paperRepository.findByConferenceFullnameAndTitle(e.getConferenceFullname(), each);
                papers.add(paper);
            }
        }
        return papers;
    }

    public Mark submitMark(String title, String username, String conferenceFullname, int score,
                           int confidence, String describe) {
        Mark mark =markRepository.findByPaperTitleAndConferenceFullname(title,conferenceFullname);
        int index = mark.getPcmembers().indexOf(username);
        mark.getFinish().set(index,true);
        mark.getScores().set(index,score);
        mark.getConfidences().set(index,confidence);
        mark.getDiscribes().set(index,describe);
        markRepository.save(mark);

        return mark;
    }
}