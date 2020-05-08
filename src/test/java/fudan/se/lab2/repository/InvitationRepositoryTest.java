package fudan.se.lab2.repository;

import fudan.se.lab2.domain.Invitation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class InvitationRepositoryTest {
    @Autowired
    InvitationRepository invitationRepository;

    @BeforeEach
    void setUp() {
        Invitation invitation1 = new Invitation();
        invitation1.setInvitingParty("hzh1");
        invitation1.setInvitedParty("hzh2");
        invitation1.setState(0);
        invitation1.setConferenceFullname("testConference1");
        invitationRepository.save(invitation1);

        Invitation invitation2 = new Invitation();
        invitation2.setInvitingParty("hzh1");
        invitation2.setInvitedParty("hzh3");
        invitation2.setState(0);
        invitation2.setConferenceFullname("testConference1");
        invitationRepository.save(invitation2);

        Invitation invitation3 = new Invitation();
        invitation3.setInvitingParty("hzh1");
        invitation3.setInvitedParty("hzh4");
        invitation3.setState(0);
        invitation3.setConferenceFullname("testConference1");
        invitationRepository.save(invitation3);


    }

    @AfterEach
    void tearDown() {
        invitationRepository.deleteAll();
    }


    @Test
    void findByInvitedParty() {
        Iterable<Invitation> invitations = invitationRepository.findByInvitedParty("hzh3");
        assertNotNull(invitations);
    }

    @Test
    void findByState() {
        Iterable<Invitation> invitations = invitationRepository.findByInvitedParty("hzh3");
        assertNotNull(invitations);
        for (Invitation e: invitations){
            assertEquals("hzh1",e.getInvitingParty());
            assertTrue(e.getInvitedParty().contains("hzh"));
        }
    }

    @Test
    void findByInvitedPartyAndConferenceFullname() {
        Invitation invitation = invitationRepository.findByInvitedPartyAndConferenceFullname("hzh2","testConference1");
        assertEquals("hzh1",invitation.getInvitingParty());
    }
}