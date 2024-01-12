package com.dom.tasks.repository;

import com.dom.tasks.domain.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface TaskRepository extends JpaRepository<Task, Long> {


    @Query(value = """
            select * from tasks t
            join users_tasks ut on t.id = ut.task_id
            where user_id = :userId
            """)
    List<Task> findAllByUserId(@Param("userId") Long userId);

}
