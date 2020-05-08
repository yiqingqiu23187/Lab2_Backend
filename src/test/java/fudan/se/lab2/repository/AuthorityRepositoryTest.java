package fudan.se.lab2.repository;

import fudan.se.lab2.domain.Authority;
import fudan.se.lab2.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AuthorityRepositoryTest {

    @Autowired
    AuthorityRepository authorityRepository ;

    @BeforeEach
    void setUp() {
        Authority authority = new Authority();
        authority.setAuthority("chair");
        authority.setUsers(new Set<User>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<User> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(User user) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends User> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }
        });
        User user1 = new User();
        user1.setUsername("username1");
        authority.getUsers().add(user1);
        User user2 = new User();
        user1.setUsername("username2");
        authority.getUsers().add(user2);
        //authorityRepository.save(authority);
    }

    @AfterEach
    void tearDown() {
        authorityRepository.deleteAll();
    }

    @Test
    void findByAuthorityTest() {
//        Authority authority = authorityRepository.findByAuthority("chair");
//        Assertions.assertNull(authority);
//        Assertions.assertTrue(authority.getUsers().contains("username1"));
//        Assertions.assertTrue(authority.getUsers().contains("username2"));
    }


}