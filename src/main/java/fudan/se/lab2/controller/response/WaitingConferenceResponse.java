package fudan.se.lab2.controller.response;

import fudan.se.lab2.domain.Conference;

import java.util.ArrayList;

public class WaitingConferenceResponse {
    ArrayList<Conference> waitingConference = new ArrayList<>();

    public ArrayList<Conference> getWaitingConference() {
        return waitingConference;
    }

    public void setWaitingConference(ArrayList<Conference> waitingConference) {
        this.waitingConference = waitingConference;
    }
}
