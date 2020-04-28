//package fudan.se.lab2.controller;
//
//import fudan.se.lab2.controller.request.ApplyConferenceRequest;
//import fudan.se.lab2.controller.request.LoginRequest;
//import fudan.se.lab2.controller.request.MyConferenceRequest;
//import fudan.se.lab2.controller.response.LoginResponse;
//import fudan.se.lab2.domain.Conference;
//import fudan.se.lab2.domain.User;
//import fudan.se.lab2.repository.ConferenceRepository;
//import fudan.se.lab2.repository.UserRepository;
//import fudan.se.lab2.service.AuthService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import fudan.se.lab2.controller.request.RegisterRequest;
//import fudan.se.lab2.domain.Authority;
//import fudan.se.lab2.domain.Conference;
//import fudan.se.lab2.domain.User;
//import fudan.se.lab2.repository.ConferenceRepository;
//import fudan.se.lab2.repository.UserRepository;
//import org.junit.Assert;
//import org.junit.jupiter.api.Assertions;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import static org.junit.Assert.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//
//import org.junit.After;
//import org.junit.Before;
//import org.springframework.http.ResponseEntity;
//
//import java.util.ArrayList;
//
//
///**
// * AuthController Tester.
// *
// * @author <Authors name>
// * @version 1.0
// * @since <pre>���� 9, 2020</pre>
// */
//@SpringBootTest
//public class AuthControllerTest {
//
//    @Autowired
//    AuthController authController;
//    @Autowired
//    AuthService authService;
//    @Autowired
//    UserRepository userRepository;
//    @Autowired
//    ConferenceRepository conferenceRepository;
//
//
//    @Before
//    public void before() throws Exception {
//    }
//
//    @After
//    public void after() throws Exception {
//    }
//
//
//    /**
//     * Method: register(@RequestBody RegisterRequest request)
//     */
//    @Test
//    public void testRegister() throws Exception {
//    //TODO: Test goes here...
//        RegisterRequest request = new RegisterRequest();
//        request.setUsername("test");
//        request.setPassword("test001");
//        request.setArea("China");
//        request.setEmail("18302010034@fudan.edu.cn");
//        request.setFullname("Huangzihao");
//        request.setJob("students");
//
//        //userRepository.deleteAll();
//        authController.register(request);
//
//        User user = userRepository.findByUsername("test");
//        System.out.println("id: "+ user.getId()+" Fullname"+user.getFullname() );
//        assertEquals("test001",user.getPassword());
//    }
//
//    /**
//     * Method: login(@RequestBody LoginRequest request)
//     */
//    @Test
//    public void testLogin() throws Exception {
//    //TODO: Test goes here...
//        LoginRequest request = new LoginRequest();
//        request.setUsername("test");
//        request.setPassword("test001");
//        assertEquals(200,authController.login(request).getStatusCodeValue());
//        User user = userRepository.findByUsername("test");
//        System.out.println("id: "+ user.getId()+" Fullname"+user.getFullname() );
//    }
//
//    /**
//     * Method: applyConfer(@RequestBody ConferenceRequest request)
//     */
//    @Test
//    public void testApplyConfer() throws Exception {
////TODO: Test goes here...
//        ApplyConferenceRequest request = new ApplyConferenceRequest();
//        request.setFullName("TestConference");
//        request.setAbbr("TC");
//        request.setHoldDate("2020-4-15");
//        request.setHoldPlace("Shanghai");
//        request.setReleaseDate("2020-4-20");
//        request.setSubmissionDeadline("2020-4-19");
//        request.setUsername("test");
//
//
//        assertEquals(200,authController.applyConfer(request).getStatusCodeValue());
//
//        Conference conference = conferenceRepository.findByFullName("TestConference");
//        System.out.println(conference.getChair());
//    }
//
//    @Test
//    public void testAllConference() throws Exception{
//        assertEquals(200,authController.allConference());
//      //  ArrayList<Conference> conferences =(ArrayList<Conference>) authController.allConference().getAllConference();
//        //System.out.println(conferences.size());
//    }
//
//    @Test
//    public  void testMyConference() throws Exception{
//        MyConferenceRequest request = new MyConferenceRequest();
//        request.setUsername("test");
//
//        assertEquals(200,authController.myConference(request).getStatusCodeValue());
//    }
//
//    @Test
//    public  void testMessage() throws Exception{
//
//    }
//
//    @Test
//    public  void testGetAllUser() throws Exception{
//
//    }
//
//    @Test
//    public  void testOpenConference() throws Exception{
//
//    }
//
////    @Test
////    public  void testMyConference() throws Exception{
////
////    }
//    /**
//     * Method: welcome()
//     */
//    @Test
//    public void testWelcome() throws Exception {
////TODO: Test goes here...
//    }
//
//
//}
//