package fudan.se.lab2.domain;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class Mark {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String paperTitle;
    private String conferenceFullname;
    private ArrayList<String> pcmembers = new ArrayList<>();
    private ArrayList<Boolean> finish = new ArrayList<>();

    private ArrayList<Integer> scores = new ArrayList<>();
    private ArrayList<Integer> confidences = new ArrayList<>();
    private ArrayList<String> discribes = new ArrayList<>();


    public ArrayList<Boolean> getFinish() {
        return finish;
    }

    public void setFinish(ArrayList<Boolean> finish) {
        this.finish = finish;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaperTitle() {
        return paperTitle;
    }

    public void setPaperTitle(String paperTitle) {
        this.paperTitle = paperTitle;
    }

    public String getConferenceFullname() {
        return conferenceFullname;
    }

    public void setConferenceFullname(String conferenceFullname) {
        this.conferenceFullname = conferenceFullname;
    }

    public ArrayList<String> getPcmembers() {
        return pcmembers;
    }

    public void setPcmembers(ArrayList<String> pcmembers) {
        this.pcmembers = pcmembers;
    }

    public ArrayList<Integer> getScores() {
        return scores;
    }

    public void setScores(ArrayList<Integer> scores) {
        this.scores = scores;
    }

    public ArrayList<Integer> getConfidences() {
        return confidences;
    }

    public void setConfidences(ArrayList<Integer> confidences) {
        this.confidences = confidences;
    }

    public ArrayList<String> getDiscribes() {
        return discribes;
    }

    public void setDiscribes(ArrayList<String> discribes) {
        this.discribes = discribes;
    }
}