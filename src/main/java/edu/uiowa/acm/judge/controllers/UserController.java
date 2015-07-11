package edu.uiowa.acm.judge.controllers;

import com.google.common.collect.Lists;
import com.mysql.jdbc.StringUtils;
import edu.uiowa.acm.judge.dao.ConfirmationDao;
import edu.uiowa.acm.judge.dao.TeamDao;
import edu.uiowa.acm.judge.dao.TeamMemberDao;
import edu.uiowa.acm.judge.mail.EmailService;
import edu.uiowa.acm.judge.models.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.UUID;

/**
 * Created by Tom on 6/21/2015.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private final Logger LOG = Logger.getLogger(UserController.class);

    @Value("${security.user.name}")
    private String adminName;

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
    public Principal user(final Principal user) {
        return user;
    }

    @RequestMapping(value = "/userExists", method = RequestMethod.GET)
    public boolean userExists(@RequestParam(value="teamName") final String teamName) {
        return userDetailsService.userExists(teamName);
    }


    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public void addUser(@RequestBody final NewUserSubmission newUser,
                        final HttpServletResponse response) {
        if(!userDetailsService.userExists(newUser.getTeamName()) && !adminName.equals(newUser.getTeamName())) {

            final User userDetails = new User(newUser.getTeamName(),
                    passwordEncoder.encode(newUser.getPassword()), Lists.newArrayList(new SimpleGrantedAuthority("USER")));
            userDetailsService.createUser(userDetails);

//            // Create our UUID for email confirmation
            final String uuid = getStringUUID();
            final int userId = confirmationDao.insert(newUser.getTeamName(), newUser.getEmail(), uuid);
            emailService.sendConfirmationEmail(newUser.getEmail(), userId, uuid);

            // Save the team
            saveTeam(newUser);

            response.setStatus(200);
        }
    }

    @RequestMapping(value = "/resendConfirmation/{userId}", method = RequestMethod.POST)
    public boolean resendConfirmation(@PathVariable("userId") final Long userId) {
        try {
            final UserConfirmation userConfirmation = confirmationDao.getUserConfirmation(userId);
            emailService.sendConfirmationEmail(userConfirmation.getEmail(), userId.intValue(), getStringUUID());
            return true;
        }
        catch (final Exception e) {
            LOG.error("An error occurred trying to resend a confirmation email to user " + userId, e);
            return false;
        }
    }

    private Team saveTeam(final NewUserSubmission newUser) {
        final Team team = new Team(Team.TeamLevel.getDivision(newUser.getDivision()), newUser.getTeamName(), newUser.getSchoolName(), newUser.getEmail());
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

    @RequestMapping(value = "/confirm/{teamId}/{uuid}", method = RequestMethod.GET)
    public boolean userExists(@PathVariable("teamId") final Long userId,
                              @PathVariable("uuid") final String uuid) {
        try {
            final UserConfirmation userConfirmation = confirmationDao.getUserConfirmation(userId);
            if (uuid.equals(userConfirmation.getUuid())) {
                confirmationDao.setConfirmed(userConfirmation);
                return true;
            } else {
                return false;
            }
        }
        catch (final EmptyResultDataAccessException e) {
            return false; //Invalid team ID
        }
    }

    protected String getStringUUID() {
        return UUID.randomUUID().toString();
    }
}
