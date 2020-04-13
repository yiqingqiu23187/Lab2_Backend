package fudan.se.lab2.repository;

import fudan.se.lab2.domain.Paper;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface PaperRepository extends CrudRepository<Paper, Long> {
    Paper findByUsername(String username);
    Iterable<Paper> findByUsernameAndConferenceFullname(String username,String conferenceFullname);

}
