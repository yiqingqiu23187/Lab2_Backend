package fudan.se.lab2.service;

import fudan.se.lab2.controller.request.AddCommentRequest;
import fudan.se.lab2.controller.response.GetCommentResponse;
import fudan.se.lab2.controller.response.MyPaperResponse;
import fudan.se.lab2.domain.*;
import fudan.se.lab2.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;

/**
 * @author LBW
 */
@Service
public class PCMemberService {
    private UserRepository userRepository;
    private ConferenceRepository conferenceRepository;
    private InvitationRepository invitationRepository;
    private PaperRepository paperRepository;
    private DistributionRepository distributionRepository;
    private MarkRepository markRepository;
    private CommentRepository commentRepository;

    @Autowired
    public PCMemberService(UserRepository userRepository, MarkRepository markRepository,
                           ConferenceRepository conferenceRepository, InvitationRepository invitationRepository,
                           CommentRepository commentRepository,
                           PaperRepository paperRepository, DistributionRepository distributionRepository) {
        this.userRepository = userRepository;
        this.markRepository = markRepository;
        this.conferenceRepository = conferenceRepository;
        this.invitationRepository = invitationRepository;
        this.paperRepository = paperRepository;
        this.distributionRepository = distributionRepository;
        this.commentRepository = commentRepository;
    }

    public Invitation handleInvitation(String username, String conferenceFullname,
                                       Boolean agreeOrNot, ArrayList<String> topics) {
        Invitation invitation = invitationRepository.
                findByInvitedPartyAndConferenceFullname(username, conferenceFullname);
        if (invitation != null) {
            if (agreeOrNot) {
                invitation.setState(1);
                invitationRepository.save(invitation);

                Conference conference = conferenceRepository.findByFullName(conferenceFullname);
                conference.getPCMembers().add(username);
                conferenceRepository.save(conference);

                User user = userRepository.findByUsername(username);
                user.getConferenceFullname().add(conferenceFullname);
                userRepository.save(user);

                Distribution distribution = new Distribution();
                distribution.setUsername(username);
                distribution.setConferenceFullname(conferenceFullname);
                distribution.setTopics(topics);
                distributionRepository.save(distribution);
            } else {
                invitation.setState(2);
                invitationRepository.save(invitation);
            }
        } else {
            System.out.println("this conference dose not exist");
        }
        return invitation;
    }


    public void myDistribution(String username, MyPaperResponse response) {
        Iterable<Distribution> distributions = distributionRepository.findByUsername(username);
        ArrayList<Paper> papers = new ArrayList<>();
        ArrayList<Boolean> finishs = new ArrayList<>();
        ArrayList<Integer> numbers = new ArrayList<>();
        for (Distribution e : distributions) {
            for (String each : e.getPaperTitles()) {

                Paper paper = paperRepository.findByConferenceFullnameAndTitle(e.getConferenceFullname(), each);
                papers.add(paper);

                Mark mark = markRepository.findByPaperTitleAndConferenceFullname(each, e.getConferenceFullname());
                int index = mark.getPcmembers().indexOf(username);
                boolean finish;
                if (mark.getFinish().get(index) > 0) finish = true;
                else finish = false;
                finishs.add(finish);

                int numberindex = e.getPaperTitles().indexOf(each);
                numbers.add(e.getNumbers().get(numberindex));
            }
        }
        response.setPapers(papers);
        response.setFinishs(finishs);
        response.setNumbers(numbers);
    }

    public void download(Long paperID, HttpServletResponse response) {


        Paper paper = paperRepository.findByid(paperID);

        String pathName = "/usr/local/paper/" + paper.getConferenceFullname() + "/" + paper.getFilename();//you should use this line if the backend runs on dcloud
        // String pathName = "C:/Users/LENOVO/Desktop/test/"+paper.getConferenceFullname()+"/"+paper.getFilename();//you should use this line and choose a path if the backend runs locally

        //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
        response.setContentType("multipart/form-data");
        //2.设置文件头：最后一个参数是设置下载文件名(假如我们叫a.pdf)
        //response.Headers.Add("Access-Control-Expose-Headers", "Content-Disposition");
        // response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setHeader("Content-Disposition", "attachment;fileName=" + paper.getFilename());
        ServletOutputStream out;
        //通过文件路径获得File对象
        File file = new File(pathName);

        try {
            FileInputStream inputStream = new FileInputStream(file);

            //3.通过response获取ServletOutputStream对象(out)
            out = response.getOutputStream();

            int b = 0;
            byte[] buffer = new byte[512];
            while (b != -1) {
                b = inputStream.read(buffer);
                //4.写到输出流(out)中
                out.write(buffer, 0, b);
            }
            inputStream.close();
            out.close();
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Mark submitMark(String title, String username, String conferenceFullname, int score,
                           int confidence, String describe) {

        //set number of distribution
        Distribution distribution = distributionRepository.findByUsernameAndConferenceFullname(username, conferenceFullname);
        int numberindex = distribution.getPaperTitles().indexOf(title);
        ArrayList<Integer> numbers = distribution.getNumbers();
        int number = numbers.get(numberindex);
        number++;
        numbers.set(numberindex, number);
        distribution.setNumbers(numbers);
        distributionRepository.save(distribution);

        //set finish of mark
        Mark mark = markRepository.findByPaperTitleAndConferenceFullname(title, conferenceFullname);
        int index = mark.getPcmembers().indexOf(username);
        mark.getFinish().set(index, number);
        mark.getScores().set(index, score);
        mark.getConfidences().set(index, confidence);
        mark.getDiscribes().set(index, describe);
        markRepository.save(mark);


        //set finish of mark, paper and conference
        int finishOfMark = mark.getFinish().get(0);
        for (int e : mark.getFinish()) {
            finishOfMark = Math.min(finishOfMark, e);
        }
        Paper paper = paperRepository.findByConferenceFullnameAndTitle(conferenceFullname, title);
        paper.setFinish(finishOfMark);
        paperRepository.save(paper);
        Iterable<Paper> papers = paperRepository.findByConferenceFullname(conferenceFullname);
        ArrayList<Paper> temp = new ArrayList<>();
        for (Paper e : papers) {
            temp.add(e);
        }
        int finishOfConference = temp.get(0).getFinish();
        for (Paper e : temp) {
            finishOfConference = Math.min(finishOfConference, e.getFinish());
        }

        Conference conference = conferenceRepository.findByFullName(conferenceFullname);
        conference.setFinish(finishOfConference);
        conferenceRepository.save(conference);


        return mark;
    }

    public void getComment(Long paperID, GetCommentResponse response) {
        Paper paper = paperRepository.findByid(paperID);
        Iterable<Comment> comments = commentRepository.findByPaperTitle(paper.getTitle());
        if (comments == null) {
            response.setComments(null);
            return;
        }
        ArrayList<Comment> temp = new ArrayList<>();
        for (Comment e : comments) {
            temp.add(e);
        }

        response.setComments(temp);
        return;
    }


    public Comment addComment(AddCommentRequest request) {
        Comment comment = new Comment();
        comment.setPaperTitle(request.getPaperTitle());
        comment.setConferenceFullname(request.getConferenceFullname());
        comment.setUsername(request.getUsername());
        comment.setComment(request.getComment());
        commentRepository.save(comment);

        return comment;
    }


}

