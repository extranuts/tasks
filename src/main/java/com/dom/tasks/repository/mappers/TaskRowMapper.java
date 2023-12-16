package com.dom.tasks.repository.mappers;

import com.dom.tasks.domain.task.Status;
import com.dom.tasks.domain.task.Task;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class TaskRowMapper {

    @SneakyThrows
    public static Task mapRow(ResultSet resultSet)  {
        if(resultSet.next()){
            Task task = new Task();
            task.setId(resultSet.getLong("task_id"));
            task.setTitle(resultSet.getString("task_title"));
            task.setDescription(resultSet.getString("task_description"));
            task.setStatus(Status.valueOf(resultSet.getString("task_status")));
            Timestamp timestamp = resultSet.getTimestamp("task_creation_date");
            if(timestamp!= null){
                task.setExpirationDate(timestamp.toLocalDateTime());
            }
            return task;
        }
        return null;
    }
}
