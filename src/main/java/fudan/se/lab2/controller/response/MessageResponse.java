package fudan.se.lab2.controller.response;

import fudan.se.lab2.domain.Conference;

import java.util.ArrayList;

public class MessageResponse {
    private ArrayList<Conference> invitation = new ArrayList<>();
    private ArrayList<Conference> application = new ArrayList<>();

    public ArrayList<Conference> getInvitation() {
        return invitation;
    }

    public void setInvitation(ArrayList<Conference> invitation) {
        this.invitation = invitation;
    }

    public ArrayList<Conference> getApplication() {
        return application;
    }

    public void setApplication(ArrayList<Conference> application) {
        this.application = application;
    }
}
