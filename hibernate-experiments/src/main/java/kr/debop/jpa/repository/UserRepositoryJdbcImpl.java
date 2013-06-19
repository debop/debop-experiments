package kr.debop.jpa.repository;

import kr.debop.jpa.domain.User;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * kr.debop.jpa.repository.UserRepositroyJdbcImpl
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 6. 19. 오후 10:36
 */
public class UserRepositoryJdbcImpl extends JdbcDaoSupport implements UserRepositoryCustom {

    private static final String COMPLICATED_SQL = "SELECT * FROM User";

    @Override
    public List<User> myCustomBatchOperation() {
        return getJdbcTemplate().query(COMPLICATED_SQL, new UserRowMapper());
    }

    private static class UserRowMapper implements ParameterizedRowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setUsername(rs.getString("username"));
            user.setLastname(rs.getString("lastname"));
            user.setFirstname(rs.getString("firstname"));

            return user;
        }
    }
}
