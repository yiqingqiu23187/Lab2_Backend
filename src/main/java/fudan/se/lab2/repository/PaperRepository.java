package fudan.se.lab2.repository;

import fudan.se.lab2.domain.Paper;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface PaperRepository extends CrudRepository<Paper, Long> {
    Paper findByid(Long id);
    Iterable<Paper> findByUsernameAndConferenceFullname(String username,String conferenceFullname);
    Iterable<Paper> findByConferenceFullname(String conferenceFullname);
    Paper findByUsernameAndConferenceFullnameAndTitle(String username,String conferenceFullname,String title);
    Paper findByConferenceFullnameAndTitle(String conferenceFullname,String title);
}
