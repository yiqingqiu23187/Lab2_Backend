package fudan.se.lab2.controller.response;

import fudan.se.lab2.domain.Conference;
import fudan.se.lab2.domain.User;

import java.util.ArrayList;

public class MyConferenceResponce {
    private ArrayList<Conference> authorConference = new ArrayList<>();
    private ArrayList<Conference> PCConference = new ArrayList<>();
    private ArrayList<Conference> ChairConference = new ArrayList<>();

    public ArrayList<Conference> getAuthorConference() {
        return authorConference;
    }

    public void setAuthorConference(ArrayList<Conference> authorConference) {
        this.authorConference = authorConference;
    }

    public ArrayList<Conference> getPCConference() {
        return PCConference;
    }

    public void setPCConference(ArrayList<Conference> PCConference) {
        this.PCConference = PCConference;
    }

    public ArrayList<Conference> getChairConference() {
        return ChairConference;
    }

    public void setChairConference(ArrayList<Conference> chairConference) {
        ChairConference = chairConference;
    }
}
