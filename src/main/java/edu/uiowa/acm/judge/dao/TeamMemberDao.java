package edu.uiowa.acm.judge.dao;

import edu.uiowa.acm.judge.models.Team;
import edu.uiowa.acm.judge.models.TeamMember;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Tom on 6/27/2015.
 */
public interface TeamMemberDao extends CrudRepository<TeamMember, Long> {
    List<TeamMember> findByTeam(Team team);
}
