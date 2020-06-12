package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.DownloadRequest;
import fudan.se.lab2.controller.request.HandleInvitationRequest;
import fudan.se.lab2.controller.request.MyConferenceRequest;
import fudan.se.lab2.controller.request.SubmitReviewRequest;
import fudan.se.lab2.domain.Distribution;
import fudan.se.lab2.repository.ConferenceRepository;
import fudan.se.lab2.repository.DistributionRepository;
import fudan.se.lab2.repository.InvitationRepository;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PCMemberControllerTest {
    @Autowired
    ConferenceRepository conferenceRepository;
    @Autowired
    InvitationRepository invitationRepository;
    @Autowired
    DistributionRepository distributionRepository;
    @Autowired
    PCMemberController pcMemberController;
    @Test
    public void test10_handleInvitation() {

        HandleInvitationRequest request = new HandleInvitationRequest();
        request.setUsername("user2");
        request.setConferenceFullname("confer1");
        request.setAgreeOrNot(true);
        ArrayList<String> topics = new ArrayList<>();
        topics.add("topic1");
        topics.add("topic2");
        request.setTopics(topics);
        pcMemberController.handleInvitation(request);

        HandleInvitationRequest request2 = new HandleInvitationRequest();
        request2.setUsername("user3");
        request2.setConferenceFullname("confer1");
        request2.setAgreeOrNot(true);
        ArrayList<String> topics2 = new ArrayList<>();
        topics2.add("topic1");
        topics2.add("topic2");
        request2.setTopics(topics2);
        pcMemberController.handleInvitation(request2);

        HandleInvitationRequest request3 = new HandleInvitationRequest();
        request3.setUsername("user4");
        request3.setConferenceFullname("confer1");
        request3.setAgreeOrNot(true);
        ArrayList<String> topics3 = new ArrayList<>();
        topics3.add("topic1");
        topics3.add("topic3");
        request3.setTopics(topics3);
        pcMemberController.handleInvitation(request3);

        Distribution distribution = distributionRepository.findByUsernameAndConferenceFullname("user2","confer1");
        assertNotNull(distribution.getTopics());
    }

    @Test
    void test13_myDistribution() {
        MyConferenceRequest request = new MyConferenceRequest();
        request.setUsername("user2");
        pcMemberController.myDistribution(request);

        request.setUsername("user3");
        pcMemberController.myDistribution(request);

        request.setUsername("user4");
        pcMemberController.myDistribution(request);
    }

    @Ignore
    void test14_download() {
    }

    @Test
    void test15_submitMark() {
        helpSubmitMark("user1");
        helpSubmitMark("user2");
        helpSubmitMark("user3");
        helpSubmitMark("user4");
        assertTrue(conferenceRepository.findByFullName("confer1").getFinish());
    }

    void helpSubmitMark(String username){
        Distribution distribution = distributionRepository.findByUsernameAndConferenceFullname(username,"confer1");
        for (int i =0;i<distribution.getPaperTitles().size();i++){
            SubmitReviewRequest request = new SubmitReviewRequest();
            request.setPaperTitle(distribution.getPaperTitles().get(i));
            request.setConferenceFullname(distribution.getConferenceFullname());
            request.setUsername(username);
            request.setConfidence(1);
            request.setDescribe(username);
            request.setScore(1);
            pcMemberController.submitMark(request);
        }

    }
}