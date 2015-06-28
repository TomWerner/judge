package edu.uiowa.acm.judge.models;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Tom on 6/22/2015.
 */
@Entity
@Table(name = "tblJDG_team")
public class Team implements Serializable {
    public enum TeamLevel {
        DIVISION_1,
        DIVISION_2
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

    public Team(final TeamLevel teamLevel, final String teamName, final String schoolName) {
        super();
        this.teamLevel = teamLevel;
        this.teamName = teamName;
        this.schoolName = schoolName;
    }

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
}
