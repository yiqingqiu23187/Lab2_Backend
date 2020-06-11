package fudan.se.lab2.controller.response;

import fudan.se.lab2.domain.Paper;

import java.util.ArrayList;

public class ChairTalkingResponse {
    ArrayList<Paper> papers;

    public ArrayList<Paper> getPapers() {
        return papers;
    }

    public void setPapers(ArrayList<Paper> papers) {
        this.papers = papers;
    }
}
