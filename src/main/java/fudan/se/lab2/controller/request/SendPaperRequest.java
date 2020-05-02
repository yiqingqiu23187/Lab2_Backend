package fudan.se.lab2.controller.request;


import java.util.ArrayList;

public class SendPaperRequest {
    String username;
    String conferenceFullname;
    String title;
    String summary;
    private ArrayList<String> writerEmail;
    private ArrayList<String> writerName;
    private ArrayList<String> writerJob;
    private ArrayList<String> writerAddress;
    private ArrayList<String> topics;

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
