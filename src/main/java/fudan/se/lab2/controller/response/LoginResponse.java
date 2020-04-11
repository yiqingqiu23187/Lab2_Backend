package fudan.se.lab2.controller.response;

import fudan.se.lab2.domain.User;

public class LoginResponse {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private User userDetail;

    public User getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(User userDetail) {
        this.userDetail = userDetail;
    }
}
