package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.*;

import fudan.se.lab2.domain.Invitation;
import fudan.se.lab2.service.PCMemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class PCMemberController {
    private PCMemberService pcMemberService;

    Logger logger = LoggerFactory.getLogger( PCMemberService.class);

    @Autowired
    public PCMemberController( PCMemberService PCMemberService) {
        this.pcMemberService = PCMemberService;
    }

    @PostMapping("/handleInvitation")
    public ResponseEntity<?> handleInvitation(@RequestBody HandleInvitationRequest request){
        Invitation invitation = pcMemberService.handleInvitation(request.getUsername(),request.getConferenceFullname(),request.getAgreeOrNot(),request.getTopics());

        return ResponseEntity.ok(invitation);
    }


}
