package fudan.se.lab2.service;

import fudan.se.lab2.controller.request.*;
import fudan.se.lab2.controller.response.*;
import fudan.se.lab2.domain.*;
import fudan.se.lab2.exception.ConferHasBeenRegisteredException;
import fudan.se.lab2.repository.*;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;


@Service
public class ChairService {
    private UserRepository userRepository;
    private ConferenceRepository conferenceRepository;
    private InvitationRepository invitationRepository;
    private PaperRepository paperRepository;
    private DistributionRepository distributionRepository;
    private MarkRepository markRepository;


    @Autowired
    public ChairService(UserRepository userRepository,DistributionRepository distributionRepository,
                       MarkRepository markRepository, ConferenceRepository conferenceRepository, InvitationRepository invitationRepository, PaperRepository paperRepository, JwtTokenUtil jwtTokenUtil) {
        this.userRepository = userRepository;
        this.markRepository = markRepository;
        this.conferenceRepository = conferenceRepository;
        this.invitationRepository = invitationRepository;
        this.distributionRepository = distributionRepository;
        this.paperRepository = paperRepository;
    }

    public Conference applyConfer(String username, ApplyConferenceRequest request, ArrayList<String> topics) throws ConferHasBeenRegisteredException {
        if (conferenceRepository.findByFullName(request.getFullName()) != null)
            throw new ConferHasBeenRegisteredException(request.getFullName());

        User user = userRepository.findByUsername(username);
        user.getConferenceFullname().add(request.getFullName());
        userRepository.save(user);


        Conference conference = new Conference(request.getAbbr(), request.getFullName(),
                request.getHoldDate(), request.getHoldPlace(), request.getSubmissionDeadline(),
                request.getReleaseDate(), request.getUsername(), false, 0, topics);
        conferenceRepository.save(conference);
        return conference;
    }

    public Iterable<User> findAllUser() {
        Iterable<User> users = userRepository.findAll();
        return users;
    }

    //open the conference so that others can hand in a paper
    public Conference openConference(String chair, String conferenceFullname, Boolean openOrNot) {
        Conference conference = conferenceRepository.findByFullName(conferenceFullname);
        if (conference.getChair().equals(chair)) {
            conference.setOpenOrNot(openOrNot);
        }
        conferenceRepository.save(conference);
        return conference;
    }

    public void invite(InviteRequest request, InviteResponse response) {
        Conference conference = conferenceRepository.findByFullName(request.getConferenceFullname());
        if (conference.getChair().equals(request.getChair())) {
            for (String username : request.getUsernames()) {
                if (invitationRepository.findByInvitedPartyAndConferenceFullname(username,request.getConferenceFullname())!=null){
                    response.setInvitedOrNot(true);
                    continue;
                }
                Invitation invitation = new Invitation();
                invitation.setInvitingParty(request.getChair());
                invitation.setInvitedParty(username);
                invitation.setConferenceFullname(request.getConferenceFullname());
                invitation.setState(0);
                invitationRepository.save(invitation);
                User user = userRepository.findByUsername(username);
                response.getUsers().add(user);
            }
        } else {
            System.out.println("Conference's chair is not the current user");
        }
    }

    public Conference openMark(String conferenceFullname, Boolean markable, String strategy) {
        Conference conference = conferenceRepository.findByFullName(conferenceFullname);
        conference.setMarkable(markable);
        conference.setOpenOrNot(false);
        conferenceRepository.save(conference);

        ArrayList<String> pcmembers = conferenceRepository.findByFullName(conferenceFullname).getPCMembers();
        ArrayList<Paper> papers = new ArrayList<>();
        Iterable<Paper> iterable = paperRepository.findByConferenceFullname(conferenceFullname);
        for (Paper e : iterable) {
            papers.add(e);
        }
        int pcsize = pcmembers.size();
        int papersize = papers.size();



        if (strategy.equals("0")) {
            for (int i = 0; i < papersize; i++) {
                //generate Mark for each paper
                String title = papers.get(i).getTitle();
                Mark mark = new Mark();
                mark.setPaperTitle(title);
                mark.setConferenceFullname(conferenceFullname);
                for (int j = 0; j < 3; j++) {
                    Distribution distribution = distributionRepository.findByUsernameAndConferenceFullname(pcmembers.get((3*i+j)%pcsize),conferenceFullname);
                    mark.getPcmembers().add(pcmembers.get((3*i+j)%pcsize));
                    mark.getFinish().add(0);
                    mark.getScores().add(0);
                    mark.getConfidences().add(0);
                    mark.getDiscribes().add("");
                    markRepository.save(mark);

                    distribution.getPaperTitles().add(title);
                    distribution.getNumbers().add(0);
                    distributionRepository.save(distribution);
                }
            }
        }


        else if (strategy.equals("1")){
            for (int i = 0; i < papersize; i++) {
                String title = papers.get(i).getTitle();
                ArrayList<String> topics = papers.get(i).getTopics();
                ArrayList<String> tempPC = new ArrayList<>();
                //find all pcmembers for a specific paper
                for (String pcmember:pcmembers){
                    Distribution distribution = distributionRepository.findByUsernameAndConferenceFullname(pcmember,conferenceFullname);
                    for (String e:topics){
                        if (distribution.getTopics().contains(e)){
                            tempPC.add(pcmember);
                            break;
                        }
                    }
                }

                //generate Mark for each paper
                Mark mark = new Mark();
                mark.setPaperTitle(title);
                mark.setConferenceFullname(conferenceFullname);

                if (tempPC.size()<3){
                    for (int j = 0; j < 3; j++) {
                        Distribution distribution = distributionRepository.findByUsernameAndConferenceFullname(pcmembers.get((3*i+j)%pcsize),conferenceFullname);
                        mark.getPcmembers().add(pcmembers.get((3*i+j)%pcsize));
                        mark.getFinish().add(0);
                        mark.getScores().add(0);
                        mark.getConfidences().add(0);
                        mark.getDiscribes().add("");
                        markRepository.save(mark);

                        distribution.getPaperTitles().add(title);
                        distribution.getNumbers().add(0);
                        distributionRepository.save(distribution);
                    }
                }else {
                    int start = (int)(Math.random()*pcsize);
                    for (int j = 0; j < 3; j++) {
                        Distribution distribution = distributionRepository.findByUsernameAndConferenceFullname(tempPC.get((start+j)%pcsize),conferenceFullname);
                        mark.getPcmembers().add(tempPC.get((start+j)%pcsize));
                        mark.getFinish().add(0);
                        mark.getScores().add(0);
                        mark.getConfidences().add(0);
                        mark.getDiscribes().add("");
                        markRepository.save(mark);

                        distribution.getPaperTitles().add(title);
                        distribution.getNumbers().add(0);
                        distributionRepository.save(distribution);
                    }
                }

            }
        }
        return conference;
    }

    public Conference releaseMark(String conferenceFullname){
        Conference conference = conferenceRepository.findByFullName(conferenceFullname);
        conference.setReleased(true);




        Iterable<Paper> papers = paperRepository.findByConferenceFullname(conferenceFullname);
        boolean allPaperNotNeedRebuttal = true;
        for(Paper paper:papers){
            Mark mark = markRepository.findByPaperTitleAndConferenceFullname(paper.getTitle(),paper.getConferenceFullname());
            ArrayList<Integer> scores = mark.getScores();
            boolean needRebuttalOrNot = false;
            for (int score:scores){
                if (score<0)needRebuttalOrNot = true;
            }
            if (needRebuttalOrNot)allPaperNotNeedRebuttal = false;

            if (!needRebuttalOrNot){
                paper.setRebuttal(true);
                paper.setFinish(paper.getFinish()+1);
                paperRepository.save(paper);

                mark.setRebuttal(true);
                ArrayList<Integer> finish = mark.getFinish();
                ArrayList<Integer> temp = new ArrayList<>();
                for (int fi:finish){
                    temp.add(fi+1);
                }
                mark.setFinish(temp);
                markRepository.save(mark);

                Iterable<Distribution> distributions = distributionRepository.findByConferenceFullname(conferenceFullname);
                for (Distribution distribution:distributions){
                    if (distribution.getPaperTitles().contains(paper.getTitle())){
                        int index = distribution.getPaperTitles().indexOf(paper.getTitle());
                        ArrayList<Integer> numbers = distribution.getNumbers();
                        numbers.set(index,numbers.get(index)+1);
                        distribution.setNumbers(numbers);
                        distributionRepository.save(distribution);
                    }
                }
            }


        }
        if (allPaperNotNeedRebuttal){
            conference.setFinish(conference.getFinish()+1);
        }

        conferenceRepository.save(conference);
        return conference;
    }

    public ArrayList<Paper> chairTalking(String username){
        Iterable<Conference> conferences = conferenceRepository.findByChair(username);
        ArrayList<Paper> papers = new ArrayList<>();
        for (Conference conference :conferences){
            Iterable<Paper> temp = paperRepository.findByConferenceFullname(conference.getFullName());
            for (Paper paper:temp){
                papers.add(paper);
            }
        }
        return papers;
    }
}