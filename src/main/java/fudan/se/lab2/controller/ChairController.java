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
import fudan.se.lab2.service.ChairService;
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


@RestController
public class ChairController {
    private ChairService chairService;

    Logger logger = LoggerFactory.getLogger(ChairService.class);

    @Autowired
    public ChairController(ChairService chairService) {
        this.chairService = chairService;
    }

    @PostMapping("/applyConference")
    public ResponseEntity<?> applyConfer(@RequestBody ApplyConferenceRequest request){
        logger.debug("ApplyForm: " + request.toString());
        Conference conference;
        try {
            conference = chairService.applyConfer(request.getUsername(),request);
        }catch (ConferHasBeenRegisteredException ex){
            return new ControllerAdvisor().handleConferHasBeenRegisteredException(ex);
        }
        return ResponseEntity.ok(conference);
    }


    @GetMapping("/getAllUser")
    public ResponseEntity<?> allUser(){
        AllUserResponse response = new AllUserResponse();
        Iterable<User> users = chairService.findAllUser();
        response.setUsers(users);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/openConference")
    public ResponseEntity<?> openConference(@RequestBody OpenConferenceRequest request){
        Conference conference = chairService.openConference(request.getChair(),request.getConferenceFullname(),request.getOpenOrNot());
        return ResponseEntity.ok(conference);
    }

    @PostMapping("/invite")
    public ResponseEntity<?> invite(@RequestBody InviteRequest request){
        InviteResponse response = new InviteResponse();
        chairService.invite(request,response);

        return ResponseEntity.ok(response);
    }


}
