package fudan.se.lab2.controller.response;

import fudan.se.lab2.domain.Conference;

import java.util.ArrayList;

public class AllConferenceResponse {
    private Iterable<Conference> allConference = new ArrayList<>();

    public Iterable<Conference> getAllConference() {
        return allConference;
    }

    public void setAllConference(Iterable allConference) {
        this.allConference = allConference;
    }
}
