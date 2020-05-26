package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.*;

import fudan.se.lab2.controller.response.GetCommentResponse;
import fudan.se.lab2.controller.response.MyPaperResponse;
import fudan.se.lab2.domain.Comment;
import fudan.se.lab2.domain.Invitation;
import fudan.se.lab2.domain.Mark;
import fudan.se.lab2.domain.Paper;
import fudan.se.lab2.service.PCMemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;


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

    @PostMapping("/myDistribution")
    public ResponseEntity<?> myDistribution(@RequestBody MyConferenceRequest request){
        MyPaperResponse response = new MyPaperResponse();
        pcMemberService.myDistribution(request.getUsername(),response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/download")
    public void download(@RequestBody DownloadRequest request,HttpServletResponse response){
        System.out.println(request.getId());
        pcMemberService.download( request.getId(),response);
    }


    @PostMapping("/submitReview")
    public ResponseEntity<?> submitMark(@RequestBody SubmitReviewRequest request){
        Mark mark = pcMemberService.submitMark(request.getPaperTitle(),request.getUsername(),request.getConferenceFullname(),request.getScore(),
                request.getConfidence(),request.getDescribe());
        return ResponseEntity.ok(mark);
    }

    @PostMapping("/getComment")
    public ResponseEntity<?> getComment(@RequestBody GetCommentRequest request){
        GetCommentResponse getCommentResponse = new GetCommentResponse();
        pcMemberService.getComment(request.getId(),getCommentResponse);

        return ResponseEntity.ok(getCommentResponse);
    }

    @PostMapping("/addComment")
    public ResponseEntity<?> addComment(@RequestBody AddCommentRequest request){
        Comment comment = pcMemberService.addComment(request);
        return ResponseEntity.ok(comment);
    }
}
