package com.dom.tasks.service;

import com.dom.tasks.domain.task.Task;

import java.util.List;

public interface TaskService {

    Task getById(Long id);

    List<Task> getAllByUserId(Long userId);

    Task update(Task task);

    Task create(Task task, Long id);

    void delete(Long id);

}
