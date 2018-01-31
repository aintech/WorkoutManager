package ru.aintech.workoutmanager.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.stereotype.Component;

/**
 * @author Yaremchuk E.N. (aka Aintech)
 */

@Component
public class UserRepositoryImplOLD implements UserRepository {
    
    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private JdbcOperations operations;
    
    @Override
    public User getUser(String username) {
//        return operations.queryForObject("select _id, _username, _password from _user where _username = '" + username + "'", new UserRowMapper(), username);
        User user = null;
        try (
            Connection con = dataSource.getConnection();
            Statement state = con.createStatement();
            ResultSet result = state.executeQuery("select _id, _username, _password from _user");
        ) {
            if (result.next()) {
                user = new User(result.getInt("_id"), result.getString("_username"), result.getString("_password"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return user;
    }

    @Override
    public User save(User user) {
        throw new IllegalStateException("Not implemented yet!");
    }
    
    private static final class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new User(
                    rs.getInt("_id"),
                    rs.getString("_username"),
                    rs.getString("_password")
            );
        }
    }
}