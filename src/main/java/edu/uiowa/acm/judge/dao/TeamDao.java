package edu.uiowa.acm.judge.dao;

import edu.uiowa.acm.judge.models.Team;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Tom on 6/21/2015.
 */
public interface TeamDao extends CrudRepository<Team, Long> {
    Team findByTeamName(final String teamName);
}
