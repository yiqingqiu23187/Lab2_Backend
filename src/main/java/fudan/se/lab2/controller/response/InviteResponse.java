package fudan.se.lab2.controller.response;

import fudan.se.lab2.domain.User;

import java.util.ArrayList;

public class InviteResponse {
    ArrayList<User> users = new ArrayList<>();

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }
}
