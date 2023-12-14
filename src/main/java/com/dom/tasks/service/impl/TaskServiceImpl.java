package com.dom.tasks.service.impl;

import com.dom.tasks.domain.task.Task;
import com.dom.tasks.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TaskServiceImpl implements TaskService {

    @Override
    public Task getById(Long id) {
        return null;
    }

    @Override
    public List<Task> getAllByUserId(Long userId) {
        return null;
    }

    @Override
    public Task update(Task task) {
        return null;
    }

    @Override
    public Task create(Task task, Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
