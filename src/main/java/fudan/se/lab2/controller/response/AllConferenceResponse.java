package fudan.se.lab2.controller.response;

import fudan.se.lab2.domain.Conference;

import java.util.ArrayList;

public class AllConferenceResponse {
    private ArrayList<Conference> allConference = new ArrayList<>();

    public ArrayList<Conference> getAllConference() {
        return allConference;
    }

    public void setAllConference(ArrayList<Conference> allConference) {
        this.allConference = allConference;
    }
}
