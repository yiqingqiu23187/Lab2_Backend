package fudan.se.lab2.service;


import fudan.se.lab2.controller.response.*;
import fudan.se.lab2.domain.Conference;
import fudan.se.lab2.domain.Paper;
import fudan.se.lab2.repository.*;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
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


    public Paper sendPaper(String username,String conferenceFullname,String title,String summary,ArrayList<String> writerName,
                           ArrayList<String>writerEmail,ArrayList<String> writerJob,ArrayList<String> writerAddress,
                           ArrayList<String> topics,MultipartFile file) {
        Paper paper = paperRepository.findByUsernameAndConferenceFullnameAndTitle(username,conferenceFullname,title);
        if (paper == null) paper = new Paper();
        paper.setUsername(username);
        paper.setConferenceFullname(conferenceFullname);
        paper.setTitle(title);
        paper.setSummary(summary);
        paper.setWriterAddress(writerAddress);
        paper.setWriterEmail(writerEmail);
        paper.setWriterJob(writerJob);
        paper.setWriterName(writerName);
        paper.setTitle(title);
        paper.setTopics(topics);
        paperRepository.save(paper);//save paper

        Conference conference = conferenceRepository.findByFullName(conferenceFullname);
        if (!conference.getAuthors().contains(username))
            conference.getAuthors().add(username);
        conferenceRepository.save(conference);//save author

        //store paper
        //Notice!!!!
        String pathName = "/usr/local/paper/"+username+"/";//you should use this line if the backend runs on dcloud
        //String pathName = "C:/Users/  yourAccount  /Desktop/test/";//you should use this line and choose a path if the backend runs locally

        File temp = new File(pathName);
        if (!temp.exists()){
            temp.mkdirs();
        }
        String pname = file.getOriginalFilename();
        pathName += pname;




        try {
            File out = new File(pathName);
            if (out.exists() && out.isFile()) out.delete();//如果文件存在，则先删除;
            file.transferTo(out);// 写入文件
            //System.out.println("文件上传成功");
        } catch (Exception e) {
            e.printStackTrace();
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