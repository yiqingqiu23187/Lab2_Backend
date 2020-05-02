package fudan.se.lab2.domain;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class Distribution {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String conferenceFullname;

    private ArrayList<String> topics = new ArrayList<>();
    private ArrayList<String> paperTitles = new ArrayList<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public String getConferenceFullname() {
        return conferenceFullname;
    }

    public void setConferenceFullname(String conferenceFullname) {
        this.conferenceFullname = conferenceFullname;
    }

    public ArrayList<String> getTopics() {
        return topics;
    }

    public void setTopics(ArrayList<String> topics) {
        this.topics = topics;
    }

    public ArrayList<String> getPaperTitles() {
        return paperTitles;
    }

    public void setPaperTitles(ArrayList<String> paperTitles) {
        this.paperTitles = paperTitles;
    }
}
