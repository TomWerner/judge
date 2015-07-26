package edu.uiowa.acm.judge.dao;

import edu.uiowa.acm.judge.models.Problem;
import edu.uiowa.acm.judge.models.TestCase;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Handles interactions with the tblJDG_test_case table
 */
public interface TestCaseDao extends CrudRepository<TestCase, Long> {
    List<TestCase> findByProblem(Problem problem);
}
