package fudan.se.lab2.controller.response;

import fudan.se.lab2.domain.Mark;

import java.util.ArrayList;

public class MyMarkResponse {
    ArrayList<Mark> marks = new ArrayList<>();

    public ArrayList<Mark> getMarks() {
        return marks;
    }

    public void setMarks(ArrayList<Mark> marks) {
        this.marks = marks;
    }
}
