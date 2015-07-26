package edu.uiowa.acm.judge.controllers;

import com.google.common.collect.Lists;
import edu.uiowa.acm.judge.dao.ProblemDao;
import edu.uiowa.acm.judge.models.Problem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles non admin interactions with problems
 */
@RestController
public class ProblemController {
    @Autowired
    private ProblemDao problemDao;

    @ResponseBody
    @RequestMapping(value = "/problem/allProblems", method = RequestMethod.GET)
    public List<Problem> getAllTeams() {
        return Lists.newArrayList(problemDao.findAll());
    }

    @ResponseBody
    @RequestMapping(value = "/problem/getProblem/{problemId}", method = RequestMethod.GET)
    public Problem getProblem(@PathVariable("problemId") final long problemId) {
        return problemDao.findOne(problemId);
    }

}
