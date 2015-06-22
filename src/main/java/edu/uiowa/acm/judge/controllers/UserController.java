package edu.uiowa.acm.judge.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tom on 6/21/2015.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private JdbcUserDetailsManager userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping("/getUser")
    public Principal user(final Principal user) {
        return user;
    }

    @RequestMapping(value="/addUser", method = RequestMethod.POST)
    public void addUser(@RequestParam(required = true) final String username,
                        @RequestParam(required = true) final String password) {
        if(!userDetailsService.userExists(username)) {
            final List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("USER"));
            final User userDetails = new User(username,
                    passwordEncoder.encode(password), authorities);

            userDetailsService.createUser(userDetails);
        }
    }
}
