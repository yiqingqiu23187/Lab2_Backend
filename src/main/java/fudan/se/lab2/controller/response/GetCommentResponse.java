package fudan.se.lab2.controller.response;

import fudan.se.lab2.domain.Comment;

import java.util.ArrayList;

public class GetCommentResponse {
    ArrayList<Comment> comments;//null if there is no comment

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }
}
