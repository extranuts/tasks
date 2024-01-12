package com.dom.tasks.repository.impl;

import com.dom.tasks.domain.exception.ResourceMappingException;
import com.dom.tasks.domain.task.Task;
import com.dom.tasks.repository.TaskRepository;
import com.dom.tasks.repository.mappers.TaskRowMapper;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

//@Repository
@RequiredArgsConstructor
public class TaskRepositoryImpl implements TaskRepository {

    private final DataSourceConfig dataSourceConfig;

    private final String FIND_BY_ID_SQL = """
            select
                t.id as task_id,
                t.title as task_title,
                t.description as task_description,
                t.expiration_date as task_expiration_date,
                t.status as task_status
            from tasks t
            where id = ?;
            """;
    private final String FIND_ALL_BY_USER_ID_SQL = """
            select t.id              as task_id,
                   t.title           as task_title,
                   t.description     as task_description,
                   t.expiration_date as task_expiration_date,
                   t.status          as task_status
            from tasks t
                     JOIN users_tasks ut on t.id = ut.task_id
            where ut.user_id = ?;
            """;

    private final String ASSIGN = """
            insert into users_tasks (task_id, user_id)
            values (?,?);""";

    private final String UPDATE = """
            update tasks
            set title =?,
                description =?,
                expiration_date =?,
                status =?
            where id =?
            """;
    private final String CREATE = """
            insert into tasks (title, description, expiration_date, status)
            values (?,?,?,?);
            """;
    private final String DELETE = """
            DELETE FROM tasks
            WHERE id = ?""";


    @Override
    public Optional<Task> findById(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_SQL);
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return Optional.ofNullable(TaskRowMapper.mapRow(resultSet));
            }
        } catch (Exception e) {
            throw new ResourceMappingException("ERROR while finding user by id");
        }
    }


    @Override
    public List<Task> findAllByUserId(Long userId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_SQL);
            statement.setLong(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return TaskRowMapper.mapRows(resultSet);
            }
        } catch (Exception e) {
            throw new ResourceMappingException("ERROR while finding user by id");
        }
    }

    @Override
    public void assignToUserById(Long taskId, Long userId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(ASSIGN);
            statement.setLong(1, taskId);
            statement.setLong(2, userId);
            statement.executeUpdate();
        } catch (Exception e) {
            throw new ResourceMappingException("ERROR while assigning user to task");
        }
    }

    @Override
    public void update(Task task) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, task.getTitle());
            statement.setString(2, task.getDescription());
            statement.setTimestamp(3, Timestamp.valueOf(task.getExpirationDate()));
            statement.setString(4, task.getStatus().name());
            statement.setLong(5, task.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            throw new ResourceMappingException("ERROR while updating task");
        }

    }

    @Override
    public void create(Task task) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE);
            statement.setString(1, task.getTitle());
            statement.setString(2, task.getDescription());
            statement.setTimestamp(3, Timestamp.valueOf(task.getExpirationDate()));
            statement.setString(4, task.getStatus().name());
            statement.executeUpdate();
        } catch (Exception e) {
            throw new ResourceMappingException("ERROR while creating task");
        }

    }

    @Override
    public void delete(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            throw new ResourceMappingException("ERROR while deleting task");
        }

    }
}
