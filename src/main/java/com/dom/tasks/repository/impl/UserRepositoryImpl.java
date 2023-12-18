package com.dom.tasks.repository.impl;

import com.dom.tasks.domain.exception.ResourceMappingException;
import com.dom.tasks.domain.user.Role;
import com.dom.tasks.domain.user.User;
import com.dom.tasks.repository.DataSourceConfig;
import com.dom.tasks.repository.UserRepository;
import com.dom.tasks.repository.mappers.TaskRowMapper;
import com.dom.tasks.repository.mappers.UserRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final DataSourceConfig dataSourceConfig;

    private final String FIND_BY_ID_SQL = """ 
            select u.id              as user_id,
                   u.name            as user_name,
                   u.username        as user_username,
                   u.password        as user_password,
                   ur.role           as user_role_role,
                   t.id              as task_id,
                   t.title           as task_title,
                   t.description     as task_description,
                   t.expiration_date as task_expiration_date,
                   t.status          as task_status
            from users u
                     left join users_roles ur on u.id = ur.user_id
                     left join users_tasks ut on u.id = ut.user_id
                     left join tasks t on t.id = ut.task_id
            WHERE u.id = ?
                """;
    private final String FIND_BY_USERNAME = """
            select u.id              as user_id,
                   u.name            as user_name,
                   u.username        as user_username,
                   u.password        as user_password,
                   ur.role           as user_role_role,
                   t.id              as task_id,
                   t.title           as task_title,
                   t.description     as task_description,
                   t.expiration_date as task_expiration_date,
                   t.status          as task_status
            from users u
                     left join users_roles ur on u.id = ur.user_id
                     left join users_tasks ut on u.id = ut.user_id
                     left join tasks t on t.id = ut.task_id
            WHERE u.username = ?
            """;
    private final String UPDATE = """
            update users
            set name =?,
                username =?,
                password =?
            WHERE id = ?;""";
    private final String CREATE = """
            insert into users (name, username, password)
            values (?,?,?);
            """;
    private final String INSERT_USER_ROLE = """
            INSERT INTO users_roles (user_id, role)
            VALUES (?,?);
            """;
    private final String DELETE = """
            DELETE FROM users
            WHERE id =?""";
    private final String IS_TASK_OWNER = """
                select exists(SELECT 1
                              from users_tasks
                              where user_id = ?
                                and task_id = ?)
            """;

    @Override
    @SneakyThrows
    public Optional<User> findById(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_SQL, ResultSet.CONCUR_READ_ONLY, ResultSet.TYPE_SCROLL_INSENSITIVE);
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return Optional.ofNullable(UserRowMapper.mapRow(resultSet));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("ERROR while finding user by id");
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_USERNAME, ResultSet.CONCUR_READ_ONLY, ResultSet.TYPE_SCROLL_INSENSITIVE);
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                return Optional.ofNullable(UserRowMapper.mapRow(resultSet));
            } catch (Exception e) {
                throw new RuntimeException("OMG ERROR while finding user by username");
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("ERROR while finding user by id");
        } catch (Exception e) {
            throw new RuntimeException("Connection error while finding user by username");
        }
    }

    @Override
    public void update(User user) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setLong(4, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            throw new ResourceMappingException("ERROR while updating user");
        } catch (Exception e) {
            throw new RuntimeException("Connection error while updating user");
        }
    }

    @Override
    public void create(User user) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                user.setId(resultSet.getLong("user_id"));
            }
        } catch (SQLException sqlException) {
            throw new ResourceMappingException("ERROR while updating user");
        } catch (Exception e) {
            throw new RuntimeException("Connection error while updating user");
        }
    }

    @Override
    public void insertUserRole(Long userId, Role role) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_ROLE);
            preparedStatement.setLong(1, userId);
            preparedStatement.setString(2, role.name());
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            throw new ResourceMappingException("ERROR while insert user");
        } catch (Exception e) {
            throw new RuntimeException("Connection error while insert user");
        }
    }

    @Override
    public boolean isTaskOwner(Long userId, Long taskId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(IS_TASK_OWNER);
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, taskId);
            preparedStatement.executeUpdate();
            try(ResultSet rs = preparedStatement.executeQuery()) {
                rs.next();
                return rs.getBoolean(1);
            }
        } catch (SQLException sqlException) {
            throw new ResourceMappingException("ERROR while is task owner operation");
        } catch (Exception e) {
            throw new RuntimeException("Connection error while is task owner operation");
        }
    }


    @Override
    public void delete(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            throw new ResourceMappingException("ERROR while deleting user");
        } catch (Exception e) {
            throw new RuntimeException("Connection error while deleting user");
        }
    }
}
