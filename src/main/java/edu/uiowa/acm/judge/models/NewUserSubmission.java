package edu.uiowa.acm.judge.models;

/**
 * Created by Tom on 7/3/2015.
 */
public class NewUserSubmission {
    private String teamName;
    private String schoolName;
    private String email;
    private String division;
    private String member1Name;
    private String member2Name;
    private String member3Name;
    private String password;

    public NewUserSubmission() {
        super();
    };

    public NewUserSubmission(final String teamName,
                             final String schoolName,
                             final String email,
                             final String division,
                             final String member1Name,
                             final String member2Name,
                             final String member3Name,
                             final String password) {
        super();
        this.teamName = teamName;
        this.schoolName = schoolName;
        this.email = email;
        this.division = division;
        this.member1Name = member1Name;
        this.member2Name = member2Name;
        this.member3Name = member3Name;
        this.password = password;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(final String teamName) {
        this.teamName = teamName;
    }

    public String getMember1Name() {
        return member1Name;
    }

    public void setMember1Name(final String member1Name) {
        this.member1Name = member1Name;
    }

    public String getMember2Name() {
        return member2Name;
    }

    public void setMember2Name(final String member2Name) {
        this.member2Name = member2Name;
    }

    public String getMember3Name() {
        return member3Name;
    }

    public void setMember3Name(final String member3Name) {
        this.member3Name = member3Name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(final String schoolName) {
        this.schoolName = schoolName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(final String division) {
        this.division = division;
    }
}
