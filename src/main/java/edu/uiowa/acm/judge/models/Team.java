package edu.uiowa.acm.judge.models;

import com.fasterxml.jackson.annotation.JsonValue;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Tom on 6/22/2015.
 */
@Entity
@Table(name = "tblJDG_team")
public class Team implements Serializable {
    public enum TeamLevel {
        DIVISION_1("Division 1 (AP)"),
        DIVISION_2("Division 2 (No AP)");

        private final String division;

        TeamLevel(final String division) {
            this.division = division;
        }

        @JsonValue
        public String value() {
            return division;
        }

        public static TeamLevel getDivision(final String division) {
            switch (division) {
                case "Division 1 (AP)":
                    return Team.TeamLevel.DIVISION_1;
                case "Division 2 (No AP)":
                    return Team.TeamLevel.DIVISION_2;
                default:
                    return null;
            }
        }
    }

    @Id
    @Column(name="team_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String teamName;

    @Column(nullable = false)
    private String schoolName;

    @Column
    private int correctAnswers;

    @Column
    private int incorrectSubmissions;

    @Column
    private TeamLevel teamLevel;

    @Column
    private String email;

    private boolean confirmed;

    public Team(final TeamLevel teamLevel,
                final String teamName,
                final String schoolName,
                final String email) {
        super();
        this.teamLevel = teamLevel;
        this.teamName = teamName;
        this.schoolName = schoolName;
        this.email = email;
    }

    public Team() {}

    public Long getId() {
        return id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(final String teamName) {
        this.teamName = teamName;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void incrementCorrectAnswers() {
        correctAnswers++;
    }

    public int getIncorrectSubmissions() {
        return incorrectSubmissions;
    }

    public void incrementIncorrectSubmissions() {
        incorrectSubmissions++;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(final String schoolName) {
        this.schoolName = schoolName;
    }

    public TeamLevel getTeamLevel() {
        return teamLevel;
    }

    public void setTeamLevel(final TeamLevel teamLevel) {
        this.teamLevel = teamLevel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(final boolean confirmed) {
        this.confirmed = confirmed;
    }
}
