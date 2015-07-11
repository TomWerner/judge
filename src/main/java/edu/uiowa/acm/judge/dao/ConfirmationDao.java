package edu.uiowa.acm.judge.dao;

import edu.uiowa.acm.judge.models.UserConfirmation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;


/**
 * Created by Tom on 7/4/2015.
 */
@Component
public class ConfirmationDao {

    private final JdbcTemplate template;

    private RowMapper<UserConfirmation> userConfirmationMapper =
            new RowMapper<UserConfirmation>() {
                @Override
                public UserConfirmation mapRow(final ResultSet rs, final int rowNum) throws SQLException {
                    return new UserConfirmation(rs.getLong("id"), rs.getString("username"), rs.getString("email"),
                            rs.getString("uuid"), rs.getBoolean("confirmed"));
                }
            };

    private static final String INSERT_USER_CONFIRMATION_SQL =
            "INSERT INTO tblJDG_user_confirmation (`username`, `email`, `uuid`) " +
            "VALUES (?, ?, ?) " +
            "ON DUPLICATE KEY UPDATE `uuid`=VALUES(`uuid`)";

    @Autowired
    public ConfirmationDao(final DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }

    public int insert(final String username, final String email, final String uuid) {
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(final Connection con) throws SQLException {
                final PreparedStatement preparedStatement =
                        con.prepareStatement(INSERT_USER_CONFIRMATION_SQL, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setObject(1, username, Types.VARCHAR);
                preparedStatement.setObject(2, email, Types.VARCHAR);
                preparedStatement.setObject(3, uuid, Types.VARCHAR);
                return preparedStatement;
            }
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    private static final String GET_USER_CONFIRMATION_BY_ID_SQL =
            "SELECT `id`, `username`, `email`, `uuid`, `confirmed` FROM tblJDG_user_confirmation WHERE `id` = ?";
    private static final String GET_USER_CONFIRMATION_BY_USERNAME_SQL =
            "SELECT `id`, `username`, `email`, `uuid`, `confirmed` FROM tblJDG_user_confirmation WHERE `username` = ?";

    public UserConfirmation getUserConfirmation(final Long id) {
        return template.queryForObject(GET_USER_CONFIRMATION_BY_ID_SQL, new Object[]{id}, userConfirmationMapper);
    }

    public UserConfirmation getUserConfirmation(final String username) {
        return template.queryForObject(GET_USER_CONFIRMATION_BY_USERNAME_SQL, new Object[]{username}, userConfirmationMapper);
    }

    private static final String SET_USER_CONFIRMED_SQL =
            "UPDATE tblJDG_user_confirmation " +
            "SET `confirmed` = true " +
            "WHERE `id` = ?";

    public void setConfirmed(final UserConfirmation user) {
        template.update(SET_USER_CONFIRMED_SQL, user.getId());
    }
}
