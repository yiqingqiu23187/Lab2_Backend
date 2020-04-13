package fudan.se.lab2.controller.response;

import fudan.se.lab2.domain.Paper;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

public class MyPaperResponse {
    ArrayList<Paper> papers = new ArrayList<>();

    public ArrayList<Paper> getPapers() {
        return papers;
    }

    public void setPapers(ArrayList<Paper> papers) {
        this.papers = papers;
    }

}
