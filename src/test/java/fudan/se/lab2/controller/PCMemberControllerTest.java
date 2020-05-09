package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.HandleInvitationRequest;
import fudan.se.lab2.domain.Distribution;
import fudan.se.lab2.repository.ConferenceRepository;
import fudan.se.lab2.repository.DistributionRepository;
import fudan.se.lab2.repository.InvitationRepository;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class PCMemberControllerTest {
    @Autowired
    ConferenceRepository conferenceRepository;
    @Autowired
    InvitationRepository invitationRepository;
    @Autowired
    DistributionRepository distributionRepository;
    @Autowired
    PCMemberController pcMemberController;
    @Test
    void test10_handleInvitation() {

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
    void myDistribution() {
    }

    @Test
    void download() {
    }

    @Test
    void submitMark() {
    }
}