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
    private String chair;
    private ArrayList<String> PCMembers;
    private ArrayList<String> authors;


    public ArrayList<String> getAuthor() {
        return authors;
    }

    public void setAuthor(ArrayList<String> authors) {
        this.authors = authors;
    }

    private int state;//state:0: To be aproved  1:Audit success   2:Audit failure

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

    public Conference(){}
    public Conference( String abbr, String fullName, String holdDate, String holdPlace, String submissionDeadline,
             String releaseDate, String chair,int state){
        this.abbr = abbr;
        this.fullName = fullName;
        this.holdDate = holdDate;
        this.holdPlace = holdPlace;
        this.submissionDeadline = submissionDeadline;
        this.releaseDate = releaseDate;
        this.chair=chair;
        this.state = state;
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
