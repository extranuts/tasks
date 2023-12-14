package com.dom.tasks.repository;

import com.dom.tasks.domain.user.Role;
import com.dom.tasks.domain.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository  {

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    void update(User user);

    void create(User user);

    void insertUserRole(Long userId, Role role);

    boolean isTaskOwner(Long userId);

    void delete(Long id);
}
