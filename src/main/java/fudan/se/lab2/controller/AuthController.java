package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.*;
import fudan.se.lab2.controller.request.MyConferenceRequest;
import fudan.se.lab2.controller.response.*;
import fudan.se.lab2.domain.Conference;
import fudan.se.lab2.domain.Invitation;
import fudan.se.lab2.domain.Paper;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.exception.ConferHasBeenRegisteredException;
import fudan.se.lab2.exception.ControllerAdvisor;
import fudan.se.lab2.exception.UNHasBeenRegisteredException;
import fudan.se.lab2.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LBW
 */
@RestController
public class AuthController {

    private AuthService authService;

    Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        logger.debug("RegistrationForm: " + request.toString());
        User user;
        try {
            user = authService.register(request);
        }catch (UNHasBeenRegisteredException ex){
            return new ControllerAdvisor().handlerUsernameHasBeenRegisteredException(ex);
        }
        HashMap<String, Long> response = new HashMap<>();
        response.put("id",user.getId());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        logger.debug("LoginForm: " + request.toString());
        User user;
        String token;
        try {
            user = authService.login(request.getUsername(),request.getPassword());
            token= authService.login(request.getUsername());
        }catch (UsernameNotFoundException ex){
            return new ControllerAdvisor().handleUsernameNotFoundException(ex);
        }catch (BadCredentialsException ex){
            return new ControllerAdvisor().handleBadCredentialsException(ex);
        }
        LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setUserDetail(user);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/applyConference")
    public ResponseEntity<?> applyConfer(@RequestBody ApplyConferenceRequest request){
        logger.debug("ApplyForm: " + request.toString());
        Conference conference;
        try {
            conference = authService.applyConfer(request.getUsername(),request);
        }catch (ConferHasBeenRegisteredException ex){
            return new ControllerAdvisor().handleConferHasBeenRegisteredException(ex);
        }
        return ResponseEntity.ok(conference);
    }

    @GetMapping("/allConference")
    public  ResponseEntity<?> allConference(){
        AllConferenceResponse response = new AllConferenceResponse();
        ArrayList<Conference> conferences = authService.findAllConference();
        response.setAllConference(conferences);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/myConference")
    public ResponseEntity<?> myConference(@RequestBody MyConferenceRequest request){

        MyConferenceResponce responce = new MyConferenceResponce();
        authService.findMyConference(request.getUsername(),responce);
        return ResponseEntity.ok(responce);
    }



    @PostMapping("/message")
    public ResponseEntity<?> information(@RequestBody MessageRequest request){
        MessageResponse responce = new MessageResponse();
        authService.getMessage(request.getUsername(),responce);
        return ResponseEntity.ok(responce);
    }

    @GetMapping("/getAllUser")
    public ResponseEntity<?> allUser(){
        AllUserResponse response = new AllUserResponse();
        Iterable<User> users = authService.findAllUser();
        response.setUsers(users);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/openConference")
    public ResponseEntity<?> openConference(@RequestBody OpenConferenceRequest request){
        Conference conference = authService.openConference(request.getChair(),request.getConferenceFullname(),request.getOpenOrNot());
        return ResponseEntity.ok(conference);
    }

    @PostMapping("/invite")
    public ResponseEntity<?> invite(@RequestBody InviteRequest request){
        InviteResponse response = new InviteResponse();
        authService.invite(request,response);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/handleInvitation")
    public ResponseEntity<?> handleInvitation(@RequestBody HandleInvitationRequest request){
        Invitation invitation = authService.handleInvitation(request.getUsername(),request.getConferenceFullname(),request.getAgreeOrNot());

        return ResponseEntity.ok(invitation);
    }


    @GetMapping("/waitingConference")
    public ResponseEntity<?> waitingConference(){
        WaitingConferenceResponse response = new WaitingConferenceResponse();
        authService.waitingConference(response);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin")
    public  ResponseEntity<?> admin(){
        AdminResponse response = new AdminResponse();
        ArrayList<Conference> waitingConference = authService.admin();
        response.setWaitingConference(waitingConference);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/handleConference")
    public ResponseEntity<?> handleConference(@RequestBody HandleConferenceRequest request){
        Conference conference = authService.handleConference(request.getConferenceFullname(),request.getAgreeOrNot());

        return ResponseEntity.ok(conference);
    }

    @PostMapping("/sendPaper")
    public ResponseEntity<?> sendPaper(HttpServletRequest request,@RequestParam(value = "file", required = false) MultipartFile file){
        Paper paper = authService.sendPaper(request,file);
        return ResponseEntity.ok(paper);
    }

    @PostMapping("/myPaper")
    public ResponseEntity<?> myPaper(@RequestBody MyPaperRequest request){
        MyPaperResponse response = new MyPaperResponse();
        authService.myPaper(request.getUsername(),request.getConferenceFullname(),response);

        return ResponseEntity.ok(response);
    }
    /**
     * This is a function to test your connectivity.
     */
    @GetMapping("/welcome")
    public ResponseEntity<?> welcome() {
        Map<String, String> response = new HashMap<>();
        String message = "Welcome to 2020 Software Engineering Lab2. ";
        response.put("message", message);
        return ResponseEntity.ok(response);
    }
}

