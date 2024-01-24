package com.dom.tasks.web.mappers;

import com.dom.tasks.domain.task.Task;
import com.dom.tasks.web.dto.task.TaskDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper extends Mappable<Task, TaskDto> {
}