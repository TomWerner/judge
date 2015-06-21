package edu.uiowa.acm.judge.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Created by Tom on 6/21/2015.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @RequestMapping("/getUser")
    public Principal user(final Principal user) {
        return user;
    }

}
