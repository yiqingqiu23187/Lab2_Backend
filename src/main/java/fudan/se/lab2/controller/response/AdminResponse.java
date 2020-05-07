package fudan.se.lab2.controller.response;

import fudan.se.lab2.domain.Conference;

import java.util.ArrayList;

public class AdminResponse {
    ArrayList<Conference> waitingConference = new ArrayList<>();
    ArrayList<Conference> agreeConference = new ArrayList<>();
    ArrayList<Conference> refuseConference = new ArrayList<>();

    public ArrayList<Conference> getAgreeConference() {
        return agreeConference;
    }

    public void setAgreeConference(ArrayList<Conference> agreeConference) {
        this.agreeConference = agreeConference;
    }

    public ArrayList<Conference> getRefuseConference() {
        return refuseConference;
    }

    public void setRefuseConference(ArrayList<Conference> refuseConference) {
        this.refuseConference = refuseConference;
    }

    public ArrayList<Conference> getWaitingConference() {
        return waitingConference;
    }

    public void setWaitingConference(ArrayList<Conference> waitingConference) {
        this.waitingConference = waitingConference;
    }
}
