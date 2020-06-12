package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.LoginRequest;
import fudan.se.lab2.controller.request.MessageRequest;
import fudan.se.lab2.controller.request.MyConferenceRequest;
import fudan.se.lab2.controller.request.RegisterRequest;
import fudan.se.lab2.domain.Distribution;
import fudan.se.lab2.domain.Invitation;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.repository.*;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserControllerTest {
    @Autowired
    private UserController userController;

    @Autowired
    private ConferenceRepository conferenceRepository;
    @Autowired
    private InvitationRepository invitationRepository;
    @Autowired
    private DistributionRepository distributionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PaperRepository paperRepository;
    @Autowired
    private MarkRepository markRepository;


    @Test
    public void test00_init(){
        userRepository.deleteAll();
        conferenceRepository.deleteAll();
        invitationRepository.deleteAll();;
        distributionRepository.deleteAll();
        markRepository.deleteAll();
        paperRepository.deleteAll();
    }

    @Test
    public void test01_register() {
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
    public void test02_login() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("user1");
        loginRequest.setPassword("password");
        userController.login(loginRequest);
    }

    @Test
    void test99_allConference() {
        userController.allConference();
    }

    @Test
    void test98_myConference() {
        MyConferenceRequest request = new MyConferenceRequest();
        request.setUsername("user1");
        userController.myConference(request);
    }

    @Test
    void test97_information() {
        MessageRequest request = new MessageRequest();
        request.setUsername("user1");
        userController.information(request);
    }

    @Test
    void test96_welcome() {
        userController.welcome();
    }
}