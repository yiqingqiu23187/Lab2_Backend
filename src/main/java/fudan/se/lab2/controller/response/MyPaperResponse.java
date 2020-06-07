package fudan.se.lab2.controller.response;

import fudan.se.lab2.domain.Paper;

import java.util.ArrayList;

public class MyPaperResponse {
    ArrayList<Paper> papers = new ArrayList<>();
    ArrayList<Boolean> finishs = new ArrayList<>();
    ArrayList<Integer> numbers = new ArrayList<>();

    public ArrayList<Integer> getNumbers() {
        return numbers;
    }

    public void setNumbers(ArrayList<Integer> numbers) {
        this.numbers = numbers;
    }

    public ArrayList<Paper> getPapers() {
        return papers;
    }

    public void setPapers(ArrayList<Paper> papers) {
        this.papers = papers;
    }

    public ArrayList<Boolean> getFinishs() {
        return finishs;
    }

    public void setFinishs(ArrayList<Boolean> finishs) {
        this.finishs = finishs;
    }
}
