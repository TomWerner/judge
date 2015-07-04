package edu.uiowa.acm.judge.models;

/**
 * Created by Tom on 7/4/2015.
 */
public class UserConfirmation {
    private Long id;
    private String username;
    private String uuid;
    private boolean confirmed;

    public UserConfirmation(final Long id, final String username, final String uuid, final boolean confirmed) {
        super();
        this.id = id;
        this.username = username;
        this.uuid = uuid;
        this.confirmed = confirmed;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getUuid() {
        return uuid;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(final boolean confirmed) {
        this.confirmed = confirmed;
    }

    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }
}
