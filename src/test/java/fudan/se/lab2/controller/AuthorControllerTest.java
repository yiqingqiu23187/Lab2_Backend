package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.MyConferenceRequest;
import fudan.se.lab2.controller.request.SendPaperRequest;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AuthorControllerTest {

    @Autowired
    private AuthorController authorController;
    @Test
    public void test11_sendPaper() {
        SendPaperRequest request1 = getSendPaperRequest("confer1", "paper1summary", "paper1", "user1", "topic1",
                "writer1", "1", "1", "1", "2", "2", "2", "2");
        SendPaperRequest request2 = getSendPaperRequest("confer1", "paper2summary", "paper2", "user2", "topic2",
                "writer1", "1", "1", "1", "2", "2", "2", "2");
        SendPaperRequest request3 = getSendPaperRequest("confer1", "paper3summary", "paper3", "user3", "topic3",
                "writer1", "1", "1", "1", "2", "2", "2", "2");
        authorController.sendPaper(request1);
        authorController.sendPaper(request2);
        authorController.sendPaper(request3);
    }

    SendPaperRequest getSendPaperRequest(String conferenceFullname, String summary, String title, String username, String topic,
                                         String writerName1, String writerAddress1, String writerEmail1, String writerJob1,
                                         String writerName2, String writerAddress2, String writerEmail2, String writerJob2) {
        SendPaperRequest request = new SendPaperRequest();
        request.setConferenceFullname(conferenceFullname);
        request.setSummary(summary);
        request.setTitle(title);
        request.setUsername(username);

        ArrayList<String> topics = new ArrayList<>();
        topics.add(topic);
        request.setTopics(topics);

        ArrayList<String> writerName = new ArrayList<>();
        writerName.add(writerName1);
        writerName.add(writerName2);
        request.setWriterName(writerName);

        ArrayList<String> writerJob = new ArrayList<>();
        writerJob.add(writerJob1);
        writerJob.add(writerJob2);
        request.setWriterJob(writerJob);

        ArrayList<String> writerAddress = new ArrayList<>();
        writerAddress.add(writerAddress1);
        writerAddress.add(writerAddress2);
        request.setWriterAddress(writerAddress);

        ArrayList<String> writerEmail = new ArrayList<>();
        writerEmail.add(writerEmail1);
        writerEmail.add(writerAddress2);
        request.setWriterEmail(writerEmail);

        return request;
    }

    @Ignore
    void sendFile() {
    }

    @Test
    void myPaper() {
    }

    @Test
    void test17_myMark() {
        MyConferenceRequest request = new MyConferenceRequest();
        request.setUsername("user1");
    }
}