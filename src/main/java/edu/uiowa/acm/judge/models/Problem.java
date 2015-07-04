package edu.uiowa.acm.judge.models;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Tom on 6/27/2015.
 */
@Entity
@Table(name = "tblJDG_problem")
public class Problem implements Serializable {

    @Id
    @Column(name="problem_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Team.TeamLevel teamLevel;

    @Column
    private String problemTitle;

    @Column
    private String problemBody;

    @Column
    private boolean enabled;

    public Problem(final Team.TeamLevel teamLevel,
                   final String problemTitle,
                   final String problemBody,
                   final boolean enabled) {
        super();
        this.teamLevel = teamLevel;
        this.problemTitle = problemTitle;
        this.problemBody = problemBody;
        this.enabled = enabled;
    }

    public Long getId() {
        return id;
    }

    public Team.TeamLevel getTeamLevel() {
        return teamLevel;
    }

    public void setTeamLevel(final Team.TeamLevel teamLevel) {
        this.teamLevel = teamLevel;
    }

    public String getProblemTitle() {
        return problemTitle;
    }

    public void setProblemTitle(final String problemTitle) {
        this.problemTitle = problemTitle;
    }

    public String getProblemBody() {
        return problemBody;
    }

    public void setProblemBody(final String problemBody) {
        this.problemBody = problemBody;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }
}
