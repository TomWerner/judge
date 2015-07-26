package edu.uiowa.acm.judge.controllers;

import com.google.common.collect.Lists;
import edu.uiowa.acm.judge.dao.ProblemDao;
import edu.uiowa.acm.judge.dao.TeamDao;
import edu.uiowa.acm.judge.dao.TeamMemberDao;
import edu.uiowa.acm.judge.dao.TestCaseDao;
import edu.uiowa.acm.judge.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contains all admin functionality
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProblemDao problemDao;

    @Autowired
    private TeamDao teamDao;

    @Autowired
    private TeamMemberDao teamMemberDao;

    @Autowired
    private TestCaseDao testCaseDao;

    @RequestMapping(value = "/initializeData", method = RequestMethod.GET)
    public void initializeData() {
        teamMemberDao.deleteAll();
        teamDao.deleteAll();
        problemDao.deleteAll();
        testCaseDao.deleteAll();
        for (int i = 0; i < 10; i++) {
            if ((i % 2) == 0) {
                final Team team = new Team(Team.TeamLevel.DIVISION_1, "Team " + i, "High School " + i, "team" + i + "@gmail.com");
                teamDao.save(team);
                teamMemberDao.save(new TeamMember(team, "Member 1"));
                teamMemberDao.save(new TeamMember(team, "Member 2"));
                teamMemberDao.save(new TeamMember(team, "Member 3"));
            }
            else {
                final Team team = new Team(Team.TeamLevel.DIVISION_2, "Team " + i, "High School " + i, "team" + i + "@gmail.com");
                teamDao.save(team);
                teamMemberDao.save(new TeamMember(team, "Member 1"));
                teamMemberDao.save(new TeamMember(team, "Member 2"));
            }
        }

        // Add the first two problems from the 2015 competition of each division
        problemDao.save(new Problem(Team.TeamLevel.DIVISION_1, "Prime Numbers", "A prime number is defined as an integer that has no integral factors except itself and 1. The only even\n" + "prime is 2. Write a program that takes an integer as input and returns the two bracketing prime numbers,\n" + "that is, the largest prime number smaller than the input and the smallest prime number greater than or\n" + "equal to the input. For example (italic font indicates user responses):\n" + "\n" +"Enter an integer: 8\n" + "The largest smaller prime number is 7\n" + "The smallest larger prime number is 11\n" + "or:\n" + "Enter an integer: 10000\n" + "The largest smaller prime number is 9973\n" + "The smallest larger prime number is 10007\n"));
        problemDao.save(new Problem(Team.TeamLevel.DIVISION_1, "Theater Seats", "A small theatre is playing host to a play. There are 10 rows of 12 seats each in the theatre. The seating\n" +"charts for each of the three performances (Friday, Saturday and Sunday) are stored in separate text files\n" + "called SeatFri.txt, SeatSat.txt and SeatSun.txt, respectively. Each text file contains a single number\n" + "indicating the base ticket price, followed by 10 rows of 12 characters, where each character represents the\n" + "status of a seat for that particular performance. The legal character designations are:\n" +"\n" + "A adult\n" + "S senior\n" + "Y youth\n" + "V vacant\n" + "\n" + "The base ticket price is the price of each Adult seat in the first three rows of the theatre. Seats in row 4\n" + "through 8 (inclusive) are discounted by 20%, while seats in row 9 and 10 carry a 30% discount. In\n" + "addition, Senior tickets are 10% less than the corresponding Adult ticket, and Youth tickets are 10% less\n" +"than the corresponding Senior ticket.\n" + "\n" + "Write a program that prompts the user for a performance, reads in the seating chart, and reports:\n" + "\n" + "The number of available seats;\n" + "The largest set of available adjacent seats available in any row; and\n" + "The expected revenue for that performance assuming no more tickets are sold.\n" +"\n" + "So, for example, if the seating chart for the Friday performance (file SeatFri.txt) looks like this:\n" + "\n" + "50\n" + "AAAAASSAAYAY\n" + "AAAAAAAAAAAA\n" + "AAAAAAAAAAAA\n" + "VVVVAAVVVVVV\n" + "VVAAVVSSVVAY\n" + "VVVVVVVVVVVV\n" + "VVVVVVVVVVVV\n" + "VVVVVVVVVVVV\n" + "AAAAAAAAAAAA\n" + "VVVVVVVVVVVV\n" + "\n" + "The expected interaction would look like (italic font indicates user responses):\n" +"\n" + "Which performance? Friday\n" + "There are 64 seats available.\n" + "There are 12 adjacent seats available.\n" + "The expected revenue is $2495.40\n"));
        problemDao.save(new Problem(Team.TeamLevel.DIVISION_2, "Right Triangles", "Write a program that reads five sets of three numbers from a file. Your program should determine if each triplet could represent the lengths of the sides of a 3-4-5 triangle and print \"Yes\" or \"No\" as appropriate. Recall a 3-4-5 triangle is a right triangle (i.e., has one 90 degree angle) where the lengths of the sides are in a 3-4-5 ratio (note that not all right triangles are 3-4-5 triangles).\n\nSo, for example, if the input file contains:\n5 3 4\n2 3 7\n4 3 5\n6 10 8\n4 4 10\n\nthe output should read:\nYes\nNo\nYes\nYes\nNo\n\nNote that (i) the lengths of the sides may be given in any order, and (ii) only integer values will be specified as inputs.\n"));
        problemDao.save(new Problem(Team.TeamLevel.DIVISION_2, "Prime Numbers", "A prime number is defined as an integer that has no integral factors except itself and 1. The only even prime is 2. Write a program that takes an integer as input and returns the next prime number greater than or equal to the input. For example (italic font indicates user responses):\n\nEnter an integer: 8\nThe next largest prime number is 11\nor:\nEnter an integer: 10000\nThe next largest prime number is 10007\n"));

    }

    @RequestMapping(value = "/answerClarification", method = RequestMethod.POST)
    public void answerQuestion(@RequestBody final ClarificationAnswer answer) {

    }

    @RequestMapping(value = "/problem/addProblem", method = RequestMethod.POST)
    public void addProblem(@RequestBody final Problem problem) {
        problemDao.save(problem);
    }

    @RequestMapping(value = "/problem/updateProblem/{problemId}", method = RequestMethod.POST)
    public void updateProblem(@PathVariable("problemId") final long problemId,
                              @RequestBody final Problem problem) {
        final Problem dbProblem = problemDao.findOne(problemId);
        dbProblem.setDivision(problem.getDivision());
        dbProblem.setProblemBody(problem.getProblemBody());
        dbProblem.setProblemTitle(problem.getProblemTitle());
        problemDao.save(dbProblem);
    }

    @ResponseBody
    @RequestMapping(value = "/testCases/getAll", method = RequestMethod.GET)
    public List<TestCase> getAllTestCases() {
        return Lists.newArrayList(testCaseDao.findAll());
    }
}
