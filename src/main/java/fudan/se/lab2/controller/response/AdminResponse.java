package fudan.se.lab2.controller.response;

import fudan.se.lab2.domain.Conference;

import java.util.ArrayList;

public class AdminResponse {
    ArrayList<Conference> waitingConference;

    public ArrayList<Conference> getWaitingConference() {
        return waitingConference;
    }

    public void setWaitingConference(ArrayList<Conference> waitingConference) {
        this.waitingConference = waitingConference;
    }
}
