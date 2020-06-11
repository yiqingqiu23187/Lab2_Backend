package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.*;
import fudan.se.lab2.controller.response.*;
import fudan.se.lab2.domain.Conference;
import fudan.se.lab2.domain.Paper;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.exception.ConferHasBeenRegisteredException;
import fudan.se.lab2.exception.ControllerAdvisor;
import fudan.se.lab2.service.ChairService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@RestController
public class ChairController {
    private ChairService chairService;

    Logger logger = LoggerFactory.getLogger(ChairService.class);

    @Autowired
    public ChairController(ChairService chairService) {
        this.chairService = chairService;
    }

    @PostMapping("/applyConference")
    public ResponseEntity<?> applyConfer(@RequestBody ApplyConferenceRequest request) {
        logger.debug("ApplyForm: " + request.toString());

        Conference conference;
        //System.out.println(topics.length);//To test
        ArrayList<String> tempTopics = new ArrayList<>();
        tempTopics.add(request.getTopic());
        if (request.getTopics() != null) {
            for (int i = 0; i < request.getTopics().length; i++) {
                String temp = request.getTopics()[i].get("value");
                if (!tempTopics.contains(temp))tempTopics.add(temp);
            }
        }
        try {
            conference = chairService.applyConfer(request.getUsername(), request, tempTopics);
        } catch (ConferHasBeenRegisteredException ex) {
            return new ControllerAdvisor().handleConferHasBeenRegisteredException(ex);
        }
        return ResponseEntity.ok(conference);
    }


    @GetMapping("/getAllUser")
    public ResponseEntity<?> allUser() {
        AllUserResponse response = new AllUserResponse();
        Iterable<User> users = chairService.findAllUser();
        response.setUsers(users);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/openConference")
    public ResponseEntity<?> openConference(@RequestBody OpenConferenceRequest request) {
        Conference conference = chairService.openConference(request.getChair(), request.getConferenceFullname(), request.getOpenOrNot());
        return ResponseEntity.ok(conference);
    }

    @PostMapping("/invite")
    public ResponseEntity<?> invite(@RequestBody InviteRequest request) {
        InviteResponse response = new InviteResponse();
        chairService.invite(request, response);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/openMark")
    public ResponseEntity<?> openMark(@RequestBody OpenMarkRequest request) {

        Conference conference = chairService.openMark(request.getConferenceFullname(), request.getMarkable(), request.getStrategy());
        return ResponseEntity.ok(conference);
    }

    @PostMapping("/releaseMark")
    public ResponseEntity<?> releaseMark(@RequestBody ReleaseMarkRequest request) {
        Conference conference = chairService.releaseMark(request.getConferenceFullname());
        return ResponseEntity.ok(conference);
    }

    @PostMapping("/chairTalking")
    public ResponseEntity<?> chairTalking(@RequestBody MyConferenceRequest request){
        ArrayList<Paper> papers = chairService.chairTalking(request.getUsername());
        ChairTalkingResponse response = new ChairTalkingResponse();
        response.setPapers(papers);
        return ResponseEntity.ok(response);
    }

}
