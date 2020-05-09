package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.HandleConferenceRequest;
import fudan.se.lab2.domain.Conference;
import fudan.se.lab2.repository.ConferenceRepository;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class AdminControllerTest {

    @Autowired
    AdminController adminController;
    @Autowired
    ConferenceRepository conferenceRepository;
    @Test
    void test04_waitingConference() {
        adminController.waitingConference();
    }

    @Test
    void test05_admin() {
        adminController.admin();
    }

    @Test
    void test06_handleConference() {
        HandleConferenceRequest request = new HandleConferenceRequest();
        request.setConferenceFullname("confer1");
        request.setAgreeOrNot(true);
        adminController.handleConference(request);

        Conference conference = conferenceRepository.findByFullName("confer1");
        assertEquals(1,conference.getState());
    }
}