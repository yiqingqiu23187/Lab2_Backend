package fudan.se.lab2;

import fudan.se.lab2.domain.Conference;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.repository.ConferenceRepository;
import fudan.se.lab2.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Lab2ApplicationTests {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ConferenceRepository conferenceRepository;
    @Test
    void contextLoads() {
    }

    @Test
    void register(){

        User user = new User();
        user.setId(2l);
        user.setUsername("test02");
        user.setFullname("test02pass");
        userRepository.save(user);

        User newuser = userRepository.findByUsername("test02");
        if (newuser != null)System.out.println(newuser+"successful register");
    }

    @Test
    void login(){
        User user = new User();
        user.setUsername("test02");
        user.setFullname("test02pass");
        User newuser = userRepository.findByUsername("test02");
        if (newuser != null)System.out.println(newuser+"successful login");
    }

    @Test void applyConfer(){
        conferenceRepository.save(new Conference());
        Conference conference = conferenceRepository.findByFullName("test01");
        System.out.println(conference);
    }

}
