package edu.uiowa.acm.judge.models;

/**
 * Created by Tom on 7/4/2015.
 */
public class Account {
    private Team team;
    private boolean confirmed;

    public Account(final Team team, final boolean confirmed) {
        this.team = team;
        this.confirmed = confirmed;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(final Team team) {
        this.team = team;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(final boolean confirmed) {
        this.confirmed = confirmed;
    }
}
