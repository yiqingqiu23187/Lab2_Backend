package fudan.se.lab2.repository;

import fudan.se.lab2.domain.Invitation;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface InvitationRepository extends CrudRepository<Invitation, Long> {
    ArrayList<Invitation> findAllByInvitingParty(String invitingParty);
    ArrayList<Invitation> findAllByInvitedParty(String invitedParty);
}
