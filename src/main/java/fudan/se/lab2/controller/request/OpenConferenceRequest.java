package fudan.se.lab2.controller.request;

public class OpenConferenceRequest {
    String chair;
    String conferenceFullname;
    Boolean openOrNot;

    public String getChair() {
        return chair;
    }

    public void setChair(String chair) {
        this.chair = chair;
    }

    public String getConferenceFullname() {
        return conferenceFullname;
    }

    public void setConferenceFullname(String conferenceFullname) {
        this.conferenceFullname = conferenceFullname;
    }

    public Boolean getOpenOrNot() {
        return openOrNot;
    }

    public void setOpenOrNot(Boolean openOrNot) {
        this.openOrNot = openOrNot;
    }
}
