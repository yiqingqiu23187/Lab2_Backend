package fudan.se.lab2.repository;

import fudan.se.lab2.domain.Mark;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class MarkRepositoryTest {

    @Autowired
    MarkRepository markRepository;
    @BeforeEach
    void setUp() {
        Mark mark = new Mark();
        mark.setConferenceFullname("hzhConference1");
        mark.setPaperTitle("paper1");
        markRepository.save(mark);
    }

    @AfterEach
    void tearDown() {
        markRepository.deleteAll();
    }

    @Test
    void findByPaperTitleAndConferenceFullname() {
        Mark mark = markRepository.findByPaperTitleAndConferenceFullname("paper1","hzhConference1");
        assertNotNull(mark);
    }
}