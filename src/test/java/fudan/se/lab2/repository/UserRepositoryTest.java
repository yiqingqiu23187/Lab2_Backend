package fudan.se.lab2.repository;

import fudan.se.lab2.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;
    @BeforeEach
    void setUp() {
        User user =new User();
        user.setUsername("hzh1");
        user.setPassword("password1");
        userRepository.save(user);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void findByUsername() {
        User user = userRepository.findByUsername("hzh1");
        assertEquals("password1",user.getPassword());
    }
}