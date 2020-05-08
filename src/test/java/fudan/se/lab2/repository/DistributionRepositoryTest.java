package fudan.se.lab2.repository;

import fudan.se.lab2.domain.Distribution;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class DistributionRepositoryTest {

    @Autowired
    DistributionRepository distributionRepository;
    @BeforeEach
    void setUp() {
        Distribution distribution = new Distribution();
        distribution.setUsername("hzh1");
        distribution.setConferenceFullname("testConference1");
        ArrayList<String > topics = new ArrayList<>();
        topics.add("topic1");
        topics.add("topic2");
        topics.add("topic3");
        topics.add("topic4");
        distribution.setTopics(topics);
        distributionRepository.save(distribution);
    }

    @AfterEach
    void tearDown() {
        distributionRepository.deleteAll();
    }

    @Test
    void findByUsernameAndConferenceFullname() {
        Distribution distribution = distributionRepository.findByUsernameAndConferenceFullname("hzh1","testConference1");
        assertNotNull(distribution);
        assertEquals(4,distribution.getTopics().size());
    }

    @Test
    void findByUsername() {
        Iterable<Distribution> distributions = distributionRepository.findByUsername("hzh1");
        assertNotNull(distributions);
    }
}