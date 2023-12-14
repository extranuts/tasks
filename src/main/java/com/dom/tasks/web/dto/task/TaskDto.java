package com.dom.tasks.web.dto.task;

import com.dom.tasks.domain.task.Status;
import com.dom.tasks.web.dto.validation.OnCreate;
import com.dom.tasks.web.dto.validation.OnUpdate;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class TaskDto {
    @NotNull(message = "Task id cannot be null", groups = OnUpdate.class)
    private Long id;
    @NotNull(message = "Task title cannot be null", groups = {OnCreate.class, OnUpdate.class})
    @Length(max = 255, message = "Task title must not exceed 255 characters", groups = {OnCreate.class, OnUpdate.class})
    private String title;
    @Length(max = 255, message = "Task description must not exceed 255", groups = {OnCreate.class, OnUpdate.class})
    private String description;

    private Status status;
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime expirationDate;

}
