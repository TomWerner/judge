package edu.uiowa.acm.judge.controllers;

import com.mysql.jdbc.StringUtils;
import edu.uiowa.acm.judge.dao.ConfirmationDao;
import edu.uiowa.acm.judge.dao.TeamDao;
import edu.uiowa.acm.judge.dao.TeamMemberDao;
import edu.uiowa.acm.judge.mail.EmailService;
import edu.uiowa.acm.judge.models.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Tom on 6/21/2015.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private final Logger LOG = Logger.getLogger(UserController.class);

    @Autowired
    private JdbcUserDetailsManager userDetailsService;

    @Autowired
    private TeamDao teamDao;

    @Autowired
    private TeamMemberDao teamMemberDao;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ConfirmationDao confirmationDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    public Account user(final Principal user) {
        final Team team = teamDao.findByTeamName(user.getName());
        final boolean confirmed = confirmationDao.getUserConfirmation(user.getName()).isConfirmed();

        return new Account(team, confirmed);
    }

    @RequestMapping(value = "/userExists", method = RequestMethod.GET)
    public boolean userExists(@RequestParam(value="teamName") final String teamName) {
        return userDetailsService.userExists(teamName);
    }


    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public void addUser(@RequestBody final NewUserSubmission newUser,
                        final HttpServletResponse response) {
        if(!userDetailsService.userExists(newUser.getTeamName())) {
            final List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("USER"));

            final User userDetails = new User(newUser.getTeamName(),
                    passwordEncoder.encode(newUser.getPassword()), authorities);
            userDetailsService.createUser(userDetails);

//            // Create our UUID for email confirmation
            final String uuid = getStringUUID();
            final int userId = confirmationDao.insert(newUser.getTeamName(), uuid);
            emailService.sendConfirmationEmail(newUser.getEmail(), userId, uuid);

            // Save the team
            saveTeam(newUser);

            response.setStatus(200);
        }
    }

    private Team saveTeam(final NewUserSubmission newUser) {
        final Team team = new Team(getDivision(newUser.getDivision()), newUser.getTeamName(), newUser.getSchoolName(), newUser.getEmail());
        teamDao.save(team);
        teamMemberDao.save(new TeamMember(team, newUser.getMember1Name()));
        if (!StringUtils.isEmptyOrWhitespaceOnly(newUser.getMember2Name())) {
            teamMemberDao.save(new TeamMember(team, newUser.getMember2Name()));
        }
        if (!StringUtils.isEmptyOrWhitespaceOnly(newUser.getMember3Name())) {
            teamMemberDao.save(new TeamMember(team, newUser.getMember3Name()));
        }

        return team;
    }

    private Team.TeamLevel getDivision(final String division) {
        switch (division) {
            case "Division 1 (AP)":
                return Team.TeamLevel.DIVISION_1;
            case "Division 2 (No AP)":
                return Team.TeamLevel.DIVISION_2;
            default:
                return null;
        }
    }

    @RequestMapping(value = "/confirm/{teamId}/{uuid}/", method = RequestMethod.GET)
    public boolean userExists(@PathVariable("teamId") final Long userId,
                              @PathVariable("uuuid") final String uuid) {
        final UserConfirmation userConfirmation = confirmationDao.getUserConfirmation(userId);
        if (uuid.equals(userConfirmation.getUuid())) {
            confirmationDao.setConfirmed(userId);
            return true;
        }
        else {
            return false;
        }
    }

    protected String getStringUUID() {
        return UUID.randomUUID().toString();
    }
}
