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

import fudan.se.lab2.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@RestController
public class AdminController {

    private AdminService adminService;

    Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    @GetMapping("/waitingConference")
    public ResponseEntity<?> waitingConference(){
        WaitingConferenceResponse response = new WaitingConferenceResponse();
        adminService.waitingConference(response);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin")
    public  ResponseEntity<?> admin(){
        AdminResponse response = new AdminResponse();
        adminService.admin(response);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/handleConference")
    public ResponseEntity<?> handleConference(@RequestBody HandleConferenceRequest request){
        Conference conference = adminService.handleConference(request.getConferenceFullname(),request.getAgreeOrNot());

        return ResponseEntity.ok(conference);
    }

}