package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.*;
import fudan.se.lab2.controller.request.MyConferenceRequest;
import fudan.se.lab2.controller.response.*;
import fudan.se.lab2.domain.*;
import fudan.se.lab2.exception.ConferHasBeenRegisteredException;
import fudan.se.lab2.exception.ControllerAdvisor;
import fudan.se.lab2.exception.UNHasBeenRegisteredException;

import fudan.se.lab2.service.AuthorService;
import fudan.se.lab2.service.AuthorService;
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
public class AuthorController {
    private AuthorService authorService;

    Logger logger = LoggerFactory.getLogger( AuthorService.class);

    @Autowired
    public AuthorController( AuthorService authorService) {
        this.authorService = authorService;
    }


    @PostMapping("/sendFile")
    public ResponseEntity<?> sendFile(@RequestParam("username")String username,@RequestParam("conferenceFullname") String conferenceFullname,
                                      @RequestParam("title")String title,@RequestParam(value = "file", required = false) MultipartFile file){
        authorService.sendFile(username,conferenceFullname,title,file);
        return ResponseEntity.ok("ok");
    }

    @PostMapping("/sendPaper")
    public ResponseEntity<?> sendPaper(@RequestBody SendPaperRequest request){
        Paper paper = authorService.sendPaper(request.getId(),request.getUsername(),request.getConferenceFullname(),request.getTitle(),
                request.getSummary(),request.getWriterName(), request.getWriterEmail(),
                request.getWriterJob(),request.getWriterAddress(), request.getTopics());
        return ResponseEntity.ok(paper);
    }


    @PostMapping("/myPaper")
    public ResponseEntity<?> myPaper(@RequestBody MyPaperRequest request){
        MyPaperResponse response = new MyPaperResponse();
        authorService.myPaper(request.getUsername(),request.getConferenceFullname(),response);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/myMark")
    public ResponseEntity<?> myMark(@RequestBody MyConferenceRequest request){
        MyMarkResponse response = new MyMarkResponse();
        response.setMarks(authorService.myMark(request.getUsername()));
        return ResponseEntity.ok(response);
    }
}
