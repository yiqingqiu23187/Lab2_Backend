package fudan.se.lab2.repository;

import fudan.se.lab2.domain.Paper;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class PaperRepositoryTest {

    @Autowired
    PaperRepository paperRepository;
    @BeforeEach
    void setUp() {
        Paper paper = new Paper();
        paper.setTitle("paper1");
        paper.setConferenceFullname("testConference1");
        paper.setUsername("hzh2");
        paperRepository.save(paper);
    }

    @AfterEach
    void tearDown() {
        paperRepository.deleteAll();
    }

    @Ignore
    void findByid() {
        Paper paper = paperRepository.findByid((long)10000);
        assertNotNull(paper);
    }

    @Test
    void findByUsernameAndConferenceFullname() {
        Iterable<Paper> papers = paperRepository.findByUsernameAndConferenceFullname("hzh2","testConference1");
        assertNotNull(papers);
    }

    @Test
    void findByConferenceFullname() {
        Iterable<Paper> papers = paperRepository.findByConferenceFullname("testConference1");
        assertNotNull(papers);
    }

    @Test
    void findByConferenceFullnameAndTitle() {
        Paper paper = paperRepository.findByConferenceFullnameAndTitle("testConference1","paper1");
       System.out.println(paper.getId());
        assertEquals("hzh2",paper.getUsername());
    }

    @Test
    void findByUsername() {
        Iterable<Paper> papers = paperRepository.findByUsername("hzh2");
        assertNotNull(papers);
    }
}