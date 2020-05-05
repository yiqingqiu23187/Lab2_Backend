package fudan.se.lab2.repository;

import fudan.se.lab2.domain.Mark;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author LBW
 */
@Repository
public interface MarkRepository extends CrudRepository<Mark, Long> {
    Mark findByPaperTitleAndConferenceFullname(String paperTitle,String conferenceFullname);
}
