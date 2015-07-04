package edu.uiowa.acm.judge.controllers;

import edu.uiowa.acm.judge.mail.MyMailSender;
import edu.uiowa.acm.judge.models.NewUserSubmission;
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
    private MyMailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    public Principal user(final Principal user) {
        LOG.info("Get user " + user);
        mailSender.placeOrder();
        return user;
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

            // User isn't enabled until email confirmation, everything else is good
            final User userDetails = new User(newUser.getTeamName(),
                    passwordEncoder.encode(newUser.getPassword()), false, true, true, true, authorities);
            userDetailsService.createUser(userDetails);


            response.setStatus(200);
        }
    }
}
