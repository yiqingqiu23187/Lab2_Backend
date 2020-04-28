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



    @PostMapping("/handleInvitation")
    public ResponseEntity<?> handleInvitation(@RequestBody HandleInvitationRequest request){
        Invitation invitation = authService.handleInvitation(request.getUsername(),request.getConferenceFullname(),request.getAgreeOrNot());

        return ResponseEntity.ok(invitation);
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

