package fudan.se.lab2.domain;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class Conference {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String abbr;

    @Column(unique = true)
    private String fullName;

    private String holdDate;
    private String holdPlace;
    private String submissionDeadline;
    private String releaseDate;
    private ArrayList<String> topics = new ArrayList<>();
    private Boolean openOrNot = false;
    private int state;//state:0: To be aproved  1:Audit success   2:Audit failure
    private Boolean markable = false;
    private Boolean finish = false;
    private Boolean released = false;


    private String chair;
    private ArrayList<String> PCMembers = new ArrayList<>();
    private ArrayList<String> authors = new ArrayList<>();


    public Boolean getFinish() {
        return finish;
    }

    public void setFinish(Boolean finish) {
        this.finish = finish;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getReleased() {
        return released;
    }

    public void setReleased(Boolean released) {
        this.released = released;
    }

    public Boolean getMarkable() {
        return markable;
    }

    public void setMarkable(Boolean markable) {
        this.markable = markable;
    }

    public Boolean getOpenOrNot() {
        return openOrNot;
    }

    public void setOpenOrNot(Boolean openOrNot) {
        this.openOrNot = openOrNot;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<String> authors) {
        this.authors = authors;
    }

    public ArrayList<String> getAuthor() {
        return authors;
    }

    public void setAuthor(ArrayList<String> authors) {
        this.authors = authors;
    }


    public String getChair() {
        return chair;
    }

    public void setChair(String chair) {
        this.chair = chair;
    }

    public ArrayList<String> getPCMembers() {
        return PCMembers;
    }

    public void setPCMembers(ArrayList<String> PCMembers) {
        this.PCMembers = PCMembers;
    }

    public Conference() {
    }

    public Conference(String abbr, String fullName, String holdDate, String holdPlace, String submissionDeadline,
                      String releaseDate, String chair, Boolean openOrNot, int state,ArrayList<String> topics) {
        this.abbr = abbr;
        this.fullName = fullName;
        this.holdDate = holdDate;
        this.holdPlace = holdPlace;
        this.topics = topics;
        this.submissionDeadline = submissionDeadline;
        this.releaseDate = releaseDate;
        this.chair = chair;
        this.state = state;
        this.openOrNot = openOrNot;
    }

    public ArrayList<String> getTopics() {
        return topics;
    }

    public void setTopics(ArrayList<String> topics) {
        this.topics = topics;
    }

    public Long getId() {
        return id;
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getHoldDate() {
        return holdDate;
    }

    public void setHoldDate(String holdDate) {
        this.holdDate = holdDate;
    }

    public String getHoldPlace() {
        return holdPlace;
    }

    public void setHoldPlace(String holdPlace) {
        this.holdPlace = holdPlace;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getSubmissionDeadline() {
        return submissionDeadline;
    }

    public void setSubmissionDeadline(String submissionDeadline) {
        this.submissionDeadline = submissionDeadline;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
