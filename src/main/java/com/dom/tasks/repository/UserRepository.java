package com.dom.tasks.repository;

import com.dom.tasks.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {


    /**
     * Finds a user by their username.
     *
     * @param username the username of the user to find
     * @return an optional user object if found, empty otherwise
     */


    Optional<User> findByUsername(String username);


    /**
     * Checks if the specified user is the owner of the specified task.
     *
     * @param userId the ID of the user
     * @param taskId the ID of the task
     * @return true if the user is the owner of the task, false otherwise
     */
    @Query(value = """
            select exists(
            SELECT 1
            from users_tasks
            where user_id = :userId
            and task_id = :taskId)
            """, nativeQuery = true)
    boolean isTaskOwner(@Param("userId") Long userId, @Param("taskId") Long taskId);

}
