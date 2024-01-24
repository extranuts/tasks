package com.dom.tasks.service;

import com.dom.tasks.domain.task.Task;
import com.dom.tasks.domain.task.TaskImage;

import java.util.List;


import java.time.Duration;

public interface TaskService {

    Task getById(Long id);

    List<Task> getAllByUserId(Long id);

    List<Task> getAllSoonTasks(Duration duration);

    Task update(Task task);

    Task create(Task task, Long userId);

    void delete(Long id);

    void uploadImage(Long id, TaskImage image);

}