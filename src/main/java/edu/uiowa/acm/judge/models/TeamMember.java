package edu.uiowa.acm.judge.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Tom on 6/27/2015.
 */
@Entity
@Table(name = "tblJDG_team_member")
public class TeamMember implements Serializable {
    @Id
    @Column(name="team_member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private String email;

    public TeamMember(@NotNull final Team team,
                      @NotNull final String name,
                      final String email) {
        super();
        this.team = team;
        this.name = name;
        this.email = email;
    }

    public TeamMember(@NotNull final Team team,
                      @NotNull final String name) {
        super();
        this.team = team;
        this.name = name;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(final Team team) {
        this.team = team;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }
}
