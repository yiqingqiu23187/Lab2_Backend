package fudan.se.lab2.repository;

import fudan.se.lab2.domain.Conference;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ConferenceRepositoryTest {

    @Autowired ConferenceRepository conferenceRepository;
    @BeforeEach
    void setUp() {
        Conference conference = new Conference();
        conference.setFullName("testConference1");
        conference.setAbbr("1");
        conference.setState(0);
        conference.setReleased(false);
        conference.setFinish(0);
        conference.setMarkable(false);
        conference.setOpenOrNot(false);
        conference.setChair("hzh1");
        ArrayList<String> topics = new ArrayList<>();
        topics.add("topic1");
        topics.add("topic2");
        topics.add("topic3");
        topics.add("topic4");
        conference.setTopics(topics);
        conferenceRepository.save(conference);
    }

    @AfterEach
    void tearDown() {
        conferenceRepository.deleteAll();
    }

    @Test
    void findByFullName() {
        Conference conference = conferenceRepository.findByFullName("testConference1");
        assertNotNull(conference);
        assertEquals("hzh1",conference.getChair());
        assertEquals("testConference1",conference.getFullName());
    }

    @Test
    void findByChair() {
        Iterable<Conference> conferences = conferenceRepository.findByChair("hzh1");
        for (Conference e:conferences){
            assertEquals("hzh1",e.getChair());
            System.out.println(e.getFullName());
        }
    }

    @Test
    void findByState() {
        Iterable<Conference> conferences = conferenceRepository.findByState(0);
        for (Conference e:conferences){
            assertEquals("hzh1",e.getChair());
            System.out.println(e.getFullName());
        }
    }
}