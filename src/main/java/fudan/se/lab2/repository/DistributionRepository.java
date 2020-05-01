package fudan.se.lab2.repository;

import fudan.se.lab2.domain.Conference;
import fudan.se.lab2.domain.Distribution;
import fudan.se.lab2.domain.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author LBW
 */
@Repository
public interface DistributionRepository extends CrudRepository<Distribution, Long> {
    User findByUsername(String username);
}
