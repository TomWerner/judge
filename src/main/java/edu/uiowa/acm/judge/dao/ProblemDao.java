package edu.uiowa.acm.judge.dao;

import edu.uiowa.acm.judge.models.Problem;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Tom on 6/27/2015.
 */
public interface ProblemDao extends CrudRepository<Problem, Long> {
}
