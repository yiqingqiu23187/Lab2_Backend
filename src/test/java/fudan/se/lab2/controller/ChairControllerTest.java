package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.*;
import fudan.se.lab2.domain.Conference;
import fudan.se.lab2.domain.Invitation;
import fudan.se.lab2.domain.Paper;
import fudan.se.lab2.repository.ConferenceRepository;
import fudan.se.lab2.repository.InvitationRepository;
import fudan.se.lab2.repository.PaperRepository;
import fudan.se.lab2.service.ChairService;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ChairControllerTest {

    @Autowired
    ChairController chairController;
    @Autowired
    ConferenceRepository conferenceRepository;
    @Autowired
    InvitationRepository invitationRepository;
    @Test
    public void test03_applyConfer() {
        ApplyConferenceRequest request = new ApplyConferenceRequest();
        request.setFullName("confer1");
        request.setUsername("user1");
        request.setTopic("topic1");
        Map<String ,String> map1 = new Map<String, String>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean containsKey(Object key) {
                return false;
            }

            @Override
            public boolean containsValue(Object value) {
                return false;
            }

            @Override
            public String get(Object key) {
                return null;
            }

            @Override
            public String put(String key, String value) {
                return null;
            }

            @Override
            public String remove(Object key) {
                return null;
            }

            @Override
            public void putAll(Map<? extends String, ? extends String> m) {

            }

            @Override
            public void clear() {

            }

            @Override
            public Set<String> keySet() {
                return null;
            }

            @Override
            public Collection<String> values() {
                return null;
            }

            @Override
            public Set<Entry<String, String>> entrySet() {
                return null;
            }
        };
        Map<String ,String> map2 = new Map<String, String>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean containsKey(Object key) {
                return false;
            }

            @Override
            public boolean containsValue(Object value) {
                return false;
            }

            @Override
            public String get(Object key) {
                return null;
            }

            @Override
            public String put(String key, String value) {
                return null;
            }

            @Override
            public String remove(Object key) {
                return null;
            }

            @Override
            public void putAll(Map<? extends String, ? extends String> m) {

            }

            @Override
            public void clear() {

            }

            @Override
            public Set<String> keySet() {
                return null;
            }

            @Override
            public Collection<String> values() {
                return null;
            }

            @Override
            public Set<Entry<String, String>> entrySet() {
                return null;
            }
        };
        Map<String ,String> map3 = new Map<String, String>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean containsKey(Object key) {
                return false;
            }

            @Override
            public boolean containsValue(Object value) {
                return false;
            }

            @Override
            public String get(Object key) {
                return null;
            }

            @Override
            public String put(String key, String value) {
                return null;
            }

            @Override
            public String remove(Object key) {
                return null;
            }

            @Override
            public void putAll(Map<? extends String, ? extends String> m) {

            }

            @Override
            public void clear() {

            }

            @Override
            public Set<String> keySet() {
                return null;
            }

            @Override
            public Collection<String> values() {
                return null;
            }

            @Override
            public Set<Entry<String, String>> entrySet() {
                return null;
            }
        };

        map1.put("value","topic2");
        map2.put("value","topic3");
        map3.put("value","topic4");

        Map[] maps = new Map[]{map1,map2,map3};
        request.setTopics(maps);
        request.setSubmissionDeadline("6-10");
        request.setReleaseDate("6-15");
        request.setHoldPlace("shanghai");
        request.setAbbr("c1");
        request.setHoldDate("9:00");

        chairController.applyConfer(request);
        Conference conference = conferenceRepository.findByFullName("confer1");
        assertNotNull(conference);
    }

    @Test
    public void test07_allUser() {
        chairController.allUser();
    }

    @Test
    public void test08_openConference() {
        OpenConferenceRequest request = new OpenConferenceRequest();
        request.setChair("user1");
        request.setConferenceFullname("confer1");
        request.setOpenOrNot(true);
        chairController.openConference(request);
        Conference conference = conferenceRepository.findByFullName("confer1");
        assertTrue(conference.getOpenOrNot());
    }

    @Test
    public void test09_invite() {
        InviteRequest request = new InviteRequest();
        request.setChair("user1");
        request.setConferenceFullname("confer1");
        request.setUsernames(new String[]{"user2","user3","user4"});
        chairController.invite(request);

        Invitation invitation = invitationRepository.findByInvitedPartyAndConferenceFullname("user2","confer1");
        assertNotNull(invitation);
    }

    @Test
    void test12_openMark() {
        OpenMarkRequest request = new OpenMarkRequest();
        request.setConferenceFullname("confer1");
        request.setMarkable(true);
        request.setStrategy("1");

        chairController.openMark(request);
    }

    @Test
    void test16_releaseMark() {
        ReleaseMarkRequest request = new ReleaseMarkRequest();
        request.setConferenceFullname("confer1");
        chairController.releaseMark(request);

        assertTrue(conferenceRepository.findByFullName("confer1").getReleased());
    }
}