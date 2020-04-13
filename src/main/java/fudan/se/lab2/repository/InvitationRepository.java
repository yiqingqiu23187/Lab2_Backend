package fudan.se.lab2.repository;

import fudan.se.lab2.domain.Invitation;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface InvitationRepository extends CrudRepository<Invitation, Long> {
    Iterable<Invitation> findByInvitingParty(String invitingParty);
    Iterable<Invitation> findByInvitedParty(String invitedParty);
    Iterable<Invitation> findByState(int state);
    Invitation findByInvitedPartyAndConferenceFullname(String invitedParty,String conferenceFullname);
}
