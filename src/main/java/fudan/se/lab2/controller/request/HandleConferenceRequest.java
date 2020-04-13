package fudan.se.lab2.controller.request;

public class HandleConferenceRequest {
    String conferenceFullname;
    Boolean agreeOrNot;

    public String getConferenceFullname() {
        return conferenceFullname;
    }

    public void setConferenceFullname(String conferenceFullname) {
        this.conferenceFullname = conferenceFullname;
    }

    public Boolean getAgreeOrNot() {
        return agreeOrNot;
    }

    public void setAgreeOrNot(Boolean agreeOrNot) {
        this.agreeOrNot = agreeOrNot;
    }
}
