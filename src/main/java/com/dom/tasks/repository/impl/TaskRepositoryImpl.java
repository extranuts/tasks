package com.dom.tasks.repository.impl;

import com.dom.tasks.domain.task.Task;
import com.dom.tasks.repository.DataSourceConfig;
import com.dom.tasks.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
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
        return Optional.empty();
    }

    @Override
    public List<Task> findAllByUserId(Long userId) {
        return null;
    }

    @Override
    public void assignToUserById(Long taskId, Long userId) {

    }

    @Override
    public void update(Task task) {

    }

    @Override
    public void create(Task task) {

    }

    @Override
    public void delete(Long id) {

    }
}
