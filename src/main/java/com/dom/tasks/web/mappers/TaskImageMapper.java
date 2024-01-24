package com.dom.tasks.web.mappers;

import com.dom.tasks.domain.task.TaskImage;
import com.dom.tasks.web.dto.task.TaskImageDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskImageMapper extends Mappable<TaskImage, TaskImageDto> {
}