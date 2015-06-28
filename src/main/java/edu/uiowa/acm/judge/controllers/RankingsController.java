package edu.uiowa.acm.judge.controllers;

import edu.uiowa.acm.judge.dao.TeamDao;
import edu.uiowa.acm.judge.dao.TeamMemberDao;
import edu.uiowa.acm.judge.models.Team;
import edu.uiowa.acm.judge.models.TeamMember;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Tom on 6/21/2015.
 */
@RestController
@RequestMapping("/rankings")
public class RankingsController {
    private final Logger LOG = Logger.getLogger(RankingsController.class);

    @Autowired
    TeamDao teamDao;
    @Autowired
    TeamMemberDao teamMemberDao;

    @RequestMapping("/addTeam")
    public Object addTeam() {
        final Team team = new Team(Team.TeamLevel.DIVISION_1, "My fun new team", "My school");
        teamDao.save(team);

        TeamMember member = new TeamMember(team, "Tom Werner");
        teamMemberDao.save(member);

        member = new TeamMember(team, "Ryan Wedoff", "ryan-wedoff@uiowa.edu");
        teamMemberDao.save(member);

        return teamMemberDao.findByTeam(team);
    }
}
