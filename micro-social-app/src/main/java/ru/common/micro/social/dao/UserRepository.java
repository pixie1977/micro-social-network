package ru.common.micro.social.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.Repository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.common.micro.social.dao.dto.User;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class UserRepository implements Repository<User, Long> {

    private final JdbcTemplate jdbcTemplateMaster;
    private final JdbcTemplate jdbcTemplateSlave1;
    private final JdbcTemplate jdbcTemplateSlave2;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplateMaster,
            JdbcTemplate jdbcTemplateSlave1,
            JdbcTemplate jdbcTemplateSlave2) {
        this.jdbcTemplateMaster = jdbcTemplateMaster;
        this.jdbcTemplateSlave1 = jdbcTemplateSlave1;
        this.jdbcTemplateSlave2 = jdbcTemplateSlave2;
    }

    public User save(User user) {
        String sql = "INSERT INTO USER_TABLE(id, login, password, firstName, lastName, city, gender, personal, age, roles)VALUES(?,?,?,?,?,?,?,?,?,?)";
        int count = jdbcTemplateMaster.update(sql,
                user.getId(),
                user.getLogin(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getCity(),
                user.getGender(),
                user.getPersonal(),
                user.getAge(),
                user.getRoles()
        );
        if (count < 1) {
            throw new RuntimeException("Cannot save user");
        }
        jdbcTemplateMaster.execute("COMMIT");
        return findById(user.getId());
    }

    public User findById(Long id) {
        String sql = "SELECT id, login, password, firstName, lastName, city, gender, personal, age, roles FROM USER_TABLE WHERE id = ?";
        // Используйте BeanPropertySqlParameterSource для хранения параметров запроса
        User user = new User();
        user.setId(id);
        return this.jdbcTemplateSlave1.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(User.class));
    }

    public User findByLogin(String login) {
        String sql = "SELECT id, login, password, firstName, lastName, city, gender, personal, age, roles FROM USER_TABLE WHERE login = ?";
        // Используйте BeanPropertySqlParameterSource для хранения параметров запроса
        User user = new User();
        user.setLogin(login);
        return this.jdbcTemplateSlave2.queryForObject(sql, new Object[]{login}, new BeanPropertyRowMapper<>(User.class));
    }

    public List<User> findByLogin(List<String> logins) {
        String inSql = String.join(",", Collections.nCopies(logins.size(), "?"));

        List<User> users = jdbcTemplateSlave1.query(
                String.format("select id, login, password, firstName, lastName, city, gender, personal, age, roles from USER_TABLE WHERE id IN (%s)", inSql),
                logins.toArray(),
                new BeanPropertyRowMapper<>(User.class));

        return users;
    }

    public List<User> findFriendsByLogin(String login) {
        String linksSql = "SELECT  DISTINCT u.*\n" +
                "FROM    USER_TABLE u\n" +
                "  INNER JOIN USER_LINK l\n" +
                "    ON u.login = l.loginTo\n" +
                "WHERE l.loginFrom = ? LIMIT 1000";

        List<User> users = jdbcTemplateSlave2.query(
                linksSql, new Object[]{login}, new BeanPropertyRowMapper<>(User.class));
        jdbcTemplateSlave2.execute("COMMIT");
        return users;
    }

    public List<User> findNotFriendsByLogin(String login) {
        String linksSql = "SELECT DISTINCT u.*\n" +
                "FROM USER_TABLE u\n" +
                "  WHERE u.login NOT IN(\n" +
                "    SELECT l.loginTo FROM USER_LINK l WHERE l.loginFrom = ?)" +
                "AND NOT(u.login = ?) LIMIT 1000";

        List<User> users = jdbcTemplateSlave2.query(
                linksSql, new Object[]{login, login}, new BeanPropertyRowMapper<>(User.class));

        return users;
    }

    public int updateFriends(List<String> friendLogins, String userLogin) {
        String sql = "INSERT INTO USER_LINK(id, loginFrom, loginTo)VALUES(?,?,?)";
        AtomicInteger counter = new AtomicInteger(0);
        friendLogins.forEach(friendLogin -> {
            int count = jdbcTemplateMaster.update(sql,
                    User.idGenerator(),
                    userLogin,
                    friendLogin);
            counter.addAndGet(count);
        });

        if (counter.get() != friendLogins.size()) {
            jdbcTemplateMaster.execute("ROLLBACK ");
            throw new RuntimeException("Cannot save user");
        }
        jdbcTemplateMaster.execute("COMMIT");
        return counter.get();
    }

    public int removeFriend(String friendLogin, String login) {
        String sql = "DELETE FROM USER_LINK WHERE loginFrom = ? AND loginTo = ?";
        int count = jdbcTemplateMaster.update(sql, login, friendLogin);
        jdbcTemplateMaster.execute("COMMIT");
        return count;
    }

    public List<User> search(String firstName, String lastName, Integer maxCount) {
        String linksSql = "SELECT DISTINCT u.* FROM USER_TABLE u WHERE u.firstName LIKE ? AND u.lastName LIKE ? ORDER BY id ASC LIMIT 0, ? ";

        List<User> users = jdbcTemplateSlave1.query(
                linksSql, new Object[]{"%"+firstName+"%", "%"+lastName+"%", maxCount}, new BeanPropertyRowMapper<>(User.class));

        return users;
    }
}

