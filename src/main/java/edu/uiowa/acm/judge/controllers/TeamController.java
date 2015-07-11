package edu.uiowa.acm.judge.controllers;

import edu.uiowa.acm.judge.dao.ConfirmationDao;
import edu.uiowa.acm.judge.dao.TeamDao;
import edu.uiowa.acm.judge.models.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tom on 7/10/2015.
 */
@RestController
@RequestMapping("/team")
public class TeamController {
    @Autowired
    private TeamDao teamDao;

    @Autowired
    private ConfirmationDao confirmationDao;

    @ResponseBody
    @RequestMapping(value = "allTeams", method = RequestMethod.GET)
    public List<Team> getAllTeams() {
        final Iterable<Team> allTeams = teamDao.findAll();
        final List<Team> teams = new ArrayList<>();

        for (final Team team : allTeams) {
            team.setConfirmed(confirmationDao.getUserConfirmation(team.getTeamName()).isConfirmed());
            teams.add(team);
        }
        return teams;
    }
}
