package edu.uiowa.acm.judge.controllers;

import edu.uiowa.acm.judge.dao.ProblemDao;
import edu.uiowa.acm.judge.models.ClarificationAnswer;
import edu.uiowa.acm.judge.models.Problem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Tom on 7/5/2015.
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProblemDao problemDao;

    @RequestMapping(value = "/answerClarification", method = RequestMethod.POST)
    public void answerQuestion(@RequestBody final ClarificationAnswer answer) {

    }

    @RequestMapping(value = "/addProblem", method = RequestMethod.POST)
    public void addProblem(@RequestBody final Problem problem) {
        problemDao.save(problem);
    }
}
