package fudan.se.lab2.domain;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class Paper {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;
    private String conferenceFullname;
    private String title;
    private String summary;
    private int finish = 0;
    private String filename;

    private ArrayList<String> topics = new ArrayList<>();
    private ArrayList<String> writerEmail = new ArrayList<>();
    private ArrayList<String> writerName = new ArrayList<>();
    private ArrayList<String> writerJob = new ArrayList<>();
    private ArrayList<String> writerAddress =new ArrayList<>();

    public int getFinish() {
        return finish;
    }

    public void setFinish(int finish) {
        this.finish = finish;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ArrayList<String> getTopics() {
        return topics;
    }

    public void setTopics(ArrayList<String> topics) {
        this.topics = topics;
    }

    public ArrayList<String> getWriterEmail() {
        return writerEmail;
    }

    public void setWriterEmail(ArrayList<String> writerEmail) {
        this.writerEmail = writerEmail;
    }

    public ArrayList<String> getWriterName() {
        return writerName;
    }

    public void setWriterName(ArrayList<String> writerName) {
        this.writerName = writerName;
    }

    public ArrayList<String> getWriterJob() {
        return writerJob;
    }

    public void setWriterJob(ArrayList<String> writerJob) {
        this.writerJob = writerJob;
    }

    public ArrayList<String> getWriterAddress() {
        return writerAddress;
    }

    public void setWriterAddress(ArrayList<String> writerAddress) {
        this.writerAddress = writerAddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getConferenceFullname() {
        return conferenceFullname;
    }

    public void setConferenceFullname(String conferenceFullname) {
        this.conferenceFullname = conferenceFullname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
