package fudan.se.lab2.controller.response;

import fudan.se.lab2.domain.User;

import java.util.ArrayList;

public class AllUserResponse {
    Iterable<User> users = new ArrayList<>();

    public Iterable<User> getUsers() {
        return users;
    }

    public void setUsers(Iterable<User> users) {
        this.users = users;
    }
}
