package fudan.se.lab2.controller.request;

import java.util.ArrayList;

public class ApplyConferenceRequest {
    private String abbr;
    private String fullName;
    private String holdDate;
    private String holdPlace;
    private String submissionDeadline;
    private String releaseDate;
    private String username;
    //private ArrayList<topic> topics;

//    public class topic{
//        String value;
//
//        public String getValue() {
//            return value;
//        }
//
//        public void setValue(String value) {
//            this.value = value;
//        }
//    }

//    public ArrayList<topic> getTopics() {
//        return topics;
//    }
//
//    public void setTopics(ArrayList<topic> topics) {
//        this.topics = topics;
//    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
