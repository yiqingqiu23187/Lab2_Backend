package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.ConferenceRequest;
import fudan.se.lab2.controller.request.LoginRequest;
import fudan.se.lab2.controller.request.RegisterRequest;
import fudan.se.lab2.domain.Conference;
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
        String token;
        try {
            token = authService.login(request.getUsername(),request.getPassword());
        }catch (UsernameNotFoundException ex){
            return new ControllerAdvisor().handleUsernameNotFoundException(ex);
        }catch (BadCredentialsException ex){
            return new ControllerAdvisor().handleBadCredentialsException(ex);
        }

        HashMap<String, String> response = new HashMap<>();
        response.put("token",token);
        System.out.println(response.toString());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/index")
    public ResponseEntity<?> applyConfer(@RequestBody ConferenceRequest request){
        logger.debug("ApplyForm: " + request.toString());

        Conference conference;
        try {
            conference = authService.applyConfer(request);
        }catch (ConferHasBeenRegisteredException ex){
            return new ControllerAdvisor().handleConferHasBeenRegisteredException(ex);
        }
        return ResponseEntity.ok(conference);
    }

    /**
     * This is a function to test your connectivity. (健康测试时，可能会用到它）.
     */
    @GetMapping("/welcome")
    public ResponseEntity<?> welcome() {
        Map<String, String> response = new HashMap<>();
        String message = "Welcome to 2020 Software Engineering Lab2. ";
        response.put("message", message);
        return ResponseEntity.ok(response);
    }

}



