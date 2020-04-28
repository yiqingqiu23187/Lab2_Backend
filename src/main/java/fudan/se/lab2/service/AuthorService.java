package fudan.se.lab2.service;

import fudan.se.lab2.controller.request.*;
import fudan.se.lab2.controller.response.*;
import fudan.se.lab2.domain.Conference;
import fudan.se.lab2.domain.Invitation;
import fudan.se.lab2.domain.Paper;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.exception.ConferHasBeenRegisteredException;
import fudan.se.lab2.exception.UNHasBeenRegisteredException;
import fudan.se.lab2.repository.*;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author LBW
 */
@Service
public class AuthorService {
    private UserRepository userRepository;
    private ConferenceRepository conferenceRepository;
    private InvitationRepository invitationRepository;
    private PaperRepository paperRepository;

    @Autowired
    public AuthorService(UserRepository userRepository, AuthorityRepository authorityRepository,
                         ConferenceRepository conferenceRepository, InvitationRepository invitationRepository, PaperRepository paperRepository, JwtTokenUtil jwtTokenUtil) {
        this.userRepository = userRepository;
        this.conferenceRepository = conferenceRepository;
        this.invitationRepository = invitationRepository;
        this.paperRepository = paperRepository;
    }


    public Paper sendPaper(HttpServletRequest request, MultipartFile file) {
        Paper paper = new Paper();
        paper.setUsername(request.getParameter("username"));
        paper.setConferenceFullname(request.getParameter("conferenceFullname"));
        paper.setTitle(request.getParameter("title"));
        paper.setSummary(request.getParameter("summary"));
        paperRepository.save(paper);//save paper

        Conference conference = conferenceRepository.findByFullName(request.getParameter("conferenceFullname"));
        if (!conference.getAuthors().contains(request.getParameter("username")))
            conference.getAuthors().add(request.getParameter("username"));
        conferenceRepository.save(conference);//save author

        //store paper
        //Notice!!!!
        String pathName = "/usr/local/paper/";//you should use this line if the backend runs on dcloud
        //String pathName = "C:/Users/  yourAccount  /Desktop";//you should use this line and choose a path if the backend runs locally
        String pname = file.getOriginalFilename();
        pathName += pname;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(pathName);
            fos.write(file.getBytes()); // 写入文件
            //System.out.println("文件上传成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return paper;
    }

    public void myPaper(String username, String conferenceFullname, MyPaperResponse response) {
        ArrayList<Paper> papers = new ArrayList<>();
        Iterable<Paper> temp = paperRepository.findByUsernameAndConferenceFullname(username, conferenceFullname);
        for (Paper each : temp) {
            papers.add(each);
        }

        response.setPapers(papers);
    }
}