package fudan.se.lab2.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Invitation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String invitingParty;
    private String invitedParty;
    private String conferenceFullname;
    private int state;//state:0: To be aproved  1: accept  2:refuse

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvitingParty() {
        return invitingParty;
    }

    public void setInvitingParty(String invitingParty) {
        this.invitingParty = invitingParty;
    }

    public String getInvitedParty() {
        return invitedParty;
    }

    public void setInvitedParty(String invitedParty) {
        this.invitedParty = invitedParty;
    }

    public String getConferenceFullname() {
        return conferenceFullname;
    }

    public void setConferenceFullname(String conferenceFullname) {
        this.conferenceFullname = conferenceFullname;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
