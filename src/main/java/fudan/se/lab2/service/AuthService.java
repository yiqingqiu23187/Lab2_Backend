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
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author LBW
 */
@Service
public class AuthService {
    private UserRepository userRepository;
    private AuthorityRepository authorityRepository;
    private ConferenceRepository conferenceRepository;
    private InvitationRepository invitationRepository;
    private PaperRepository paperRepository;
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public AuthService(UserRepository userRepository, AuthorityRepository authorityRepository,
    ConferenceRepository conferenceRepository,InvitationRepository invitationRepository,PaperRepository paperRepository,JwtTokenUtil jwtTokenUtil) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.conferenceRepository = conferenceRepository;
        this.invitationRepository = invitationRepository;
        this.paperRepository = paperRepository;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public User register(RegisterRequest request) throws UNHasBeenRegisteredException {
        if(userRepository.findByUsername(request.getUsername()) != null){
            throw new UNHasBeenRegisteredException(request.getUsername());
        }
        User user = new User(request.getUsername(),request.getPassword(),
                request.getFullname(),request.getEmail(),request.getArea(),request.getJob());
        userRepository.save(user);
        return user;
    }

    //return user
    public User login(String username, String password) throws UsernameNotFoundException, BadCredentialsException {
        User user = userRepository.findByUsername(username);
        if (user == null)throw new UsernameNotFoundException(username);
        if (!password.equals(user.getPassword()))throw new BadCredentialsException(username);

        return user;
    }
    //return token
    public String login(String username) throws UsernameNotFoundException, BadCredentialsException {
        User user = userRepository.findByUsername(username);
        if (user == null)throw new UsernameNotFoundException(username);

        return jwtTokenUtil.generateToken(user);
    }


    public Conference applyConfer(String username,ApplyConferenceRequest request) throws ConferHasBeenRegisteredException {
        if(conferenceRepository.findByFullName(request.getFullName()) != null)
            throw new ConferHasBeenRegisteredException(request.getFullName());
        User user = userRepository.findByUsername(username);
        user.getConferenceFullname().add(request.getFullName());
        userRepository.save(user);
        Conference conference = new Conference(request.getAbbr(),request.getFullName(),
                request.getHoldDate(),request.getHoldPlace(),request.getSubmissionDeadline(),
                request.getReleaseDate(),request.getUsername(),false,0);
        conferenceRepository.save(conference);
        return conference;
    }


    public ArrayList<Conference> findAllConference(){
        ArrayList<Conference> result = new ArrayList<>();
        Iterable<Conference> conferences =  conferenceRepository.findByState(1);
        for (Conference each:conferences){
            result.add(each);
        }
        return result;
    }

    public Iterable<User> findAllUser(){
        Iterable<User> users = userRepository.findAll();
        return users;
    }

    public void findMyConference(String username,MyConferenceResponce responce){
        User user = userRepository.findByUsername(username);
        ArrayList<String> conferenceFullname = user.getConferenceFullname();
        for (String each:conferenceFullname){
            Conference conference = conferenceRepository.findByFullName(each);
            if (conference.getChair().equals(username))responce.getChairConference().add(conference);
            if (conference.getPCMembers().contains(username))responce.getPCConference().add(conference);
            if (conference.getAuthor().contains(username))responce.getAuthorConference().add(conference);
        }
    }

    public void getMessage(String username, MessageResponse response){
        ArrayList<Invitation> invitations = new ArrayList<>();
        Iterable<Invitation> invitations1 = invitationRepository.findByInvitedParty(username);
        for (Invitation each:invitations1){
            invitations.add(each);
        }

        ArrayList<Conference> invitation = new ArrayList<>();

        for (Invitation each: invitations){
            invitation.add(conferenceRepository.findByFullName(each.getConferenceFullname()));
        }

        Iterable<Conference> application = conferenceRepository.findByChair(username);
        ArrayList<Conference> applications = new ArrayList<>();
        for (Conference each:application){
            applications.add(each);
        }
        response.setInvitation(invitation);
        response.setApplication(applications);
    }

    public Conference openConference(String chair,String conferenceFullname,Boolean openOrNot){
        Conference conference = conferenceRepository.findByFullName(conferenceFullname);
        if (conference.getChair().equals(chair)){
            conference.setOpenOrNot(openOrNot);
        }
        conferenceRepository.save(conference);
        return conference;
    }

    public void invite(InviteRequest request, InviteResponse response){
        Conference conference = conferenceRepository.findByFullName(request.getConferenceFullname());
        if (conference.getChair().equals(request.getChair())){
            for (String username:request.getUsernames()) {
                Invitation invitation = new Invitation();
                invitation.setInvitingParty(request.getChair());
                invitation.setInvitedParty(username);
                invitation.setConferenceFullname(request.getConferenceFullname());
                invitation.setState(0);
                invitationRepository.save(invitation);
                User user = userRepository.findByUsername(username);
                response.getUsers().add(user);
            }
        }else {
            System.out.println("Conference's chair is not the current user");
        }
    }

    public Invitation handleInvitation(String username,String conferenceFullname,Boolean agreeOrNot){
        Invitation invitation = invitationRepository.
                findByInvitedPartyAndConferenceFullname(username,conferenceFullname);
        if (invitation!=null){
            if (agreeOrNot){
                invitation.setState(1);
                invitationRepository.save(invitation);
                Conference conference = conferenceRepository.findByFullName(conferenceFullname);
                conference.getPCMembers().add(username);
                conferenceRepository.save(conference);
                User user = userRepository.findByUsername(username);
                user.getConferenceFullname().add(conferenceFullname);
                userRepository.save(user);
            }else {
                invitation.setState(2);
                invitationRepository.save(invitation);
            }
        }else {
            System.out.println("this conference dose not exist");
        }
        return invitation;
    }


    public void waitingConference(WaitingConferenceResponse response){
        ArrayList<Invitation> invitations = new ArrayList<>();
         Iterable<Invitation> temp      = invitationRepository.findByState(0);
        for (Invitation each:temp){
            invitations.add(each);
        }


        for (Invitation each:invitations) {
            Conference conference = conferenceRepository.findByFullName(each.getConferenceFullname());
            response.getWaitingConference().add(conference);
        }
    }

    public ArrayList<Conference> admin(){
        ArrayList<Conference> result = new ArrayList<>();
        Iterable<Conference> conferences =  conferenceRepository.findByState(0);

        for (Conference each:conferences){
            result.add(each);
        }
        System.out.println(result.size());
        return result;
    }

    public Conference handleConference(String conferenceFullname,Boolean agreeOrNot){
        Conference conference = conferenceRepository.findByFullName(conferenceFullname);
        if (agreeOrNot)conference.setState(1);
        else conference.setState(2);
        conferenceRepository.save(conference);
        return conference;
    }

    public Paper sendPaper(SendPaperRequest request){
        Paper paper = new Paper();
        paper.setUsername(request.getUsername());
        paper.setConferenceFullname(request.getConferenceFullname());
        paper.setTitle(request.getTitle());
        paper.setSummary(request.getSummary());
        paperRepository.save(paper);

        //store paper
        MultipartFile file = request.getFile();
        String pathName = "/usr/local/paper";//address
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

    public void myPaper(String username, String conferenceFullname, MyPaperResponse response){
        ArrayList<Paper> papers = new ArrayList<>();
        Iterable<Paper> temp =paperRepository.findByUsernameAndConferenceFullname(username,conferenceFullname);
        for (Paper each:temp){
            papers.add(each);
        }

        response.setPapers(papers);
    }
}
