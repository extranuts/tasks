package com.dom.tasks.web.dto.user;

import com.dom.tasks.web.dto.validation.OnCreate;
import com.dom.tasks.web.dto.validation.OnUpdate;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UserDto {

    @NotNull(message = "ID must not be null", groups = OnUpdate.class)
    private Long id;
    @NotNull(message = "Name must not be null", groups = {OnUpdate.class, OnUpdate.class})
    @Length(max = 255, message = "Name must not exceed 255 characters", groups = {OnUpdate.class, OnUpdate.class})
    private String name;
    @NotNull(message = "Username must not be null", groups = {OnUpdate.class, OnUpdate.class})
    @Length(max = 255, message = "Username must not exceed 255 characters", groups = {OnUpdate.class, OnUpdate.class})
    private String username;
    @NotNull(message = "Password must not be null", groups = {OnUpdate.class, OnCreate.class})
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @NotNull(message = "Password confirmation must not be null", groups = OnCreate.class)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String passwordConfirmation;

}
