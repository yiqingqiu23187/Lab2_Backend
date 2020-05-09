package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.LoginRequest;
import fudan.se.lab2.controller.request.RegisterRequest;
import fudan.se.lab2.domain.Distribution;
import fudan.se.lab2.domain.Invitation;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.repository.ConferenceRepository;
import fudan.se.lab2.repository.DistributionRepository;
import fudan.se.lab2.repository.InvitationRepository;
import fudan.se.lab2.repository.UserRepository;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class UserControllerTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserController userController;
    @Autowired
    private ConferenceRepository conferenceRepository;
    @Autowired
    private InvitationRepository invitationRepository;
    @Autowired
    private DistributionRepository distributionRepository;
    

    @Test
    void test00_init(){
        userRepository.deleteAll();
        conferenceRepository.deleteAll();
    }

    @Test
    void test01_register() {
        //Create an admin if not exists.
        if (userRepository.findByUsername("admin") == null) {
            User admin = new User(
                    "admin",
                    "password",
                    "TA",
                    "1@fudan.edu.cn",
                    "China",
                    "student"
            );
            userRepository.save(admin);
        } else {
            User user = userRepository.findByUsername("admin");
            user.setPassword("password");
            userRepository.save(user);
        }

        RegisterRequest registerRequest1 = addNewUser("user1","password","1@1.com","China","student","user1");
        RegisterRequest registerRequest2 = addNewUser("user2","password","2@2.com","China","student","user2");
        RegisterRequest registerRequest3 = addNewUser("user3","password","3@3.com","China","student","user3");
        RegisterRequest registerRequest4 = addNewUser("user4","password","4@4.com","China","student","user4");
        userController.register(registerRequest1);
        userController.register(registerRequest2);
        userController.register(registerRequest3);
        userController.register(registerRequest4);

        assertNotNull(userRepository.findByUsername("admin"));
        assertNotNull(userRepository.findByUsername("user1"));
        assertNotNull(userRepository.findByUsername("user2"));
        assertNotNull(userRepository.findByUsername("user3"));
        assertNotNull(userRepository.findByUsername("user4"));
    }

    RegisterRequest addNewUser(String username,String password,String email,String area,String job,String fullname){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername(username);
        registerRequest.setPassword(password);
        registerRequest.setEmail(email);
        registerRequest.setFullname(fullname);
        registerRequest.setArea(area);
        registerRequest.setJob(job);
        return registerRequest;
    }


    @Test
    void test02_login() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("user1");
        loginRequest.setPassword("password");
        ResponseEntity<?> responseEntity =userController.login(loginRequest);
    }

    @Test
    void allConference() {
    }

    @Test
    void myConference() {
    }

    @Test
    void information() {
    }

    @Test
    void welcome() {
    }
}