package com.dom.tasks.web.dto.user;

import com.dom.tasks.web.dto.validation.OnCreate;
import com.dom.tasks.web.dto.validation.OnUpdate;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Length;

@Data
@Schema(description = "User DTO")

public class UserDto {

    @Schema(description = "User id", example = "1")
    @NotNull(message = "ID must not be null", groups = OnUpdate.class)
    private Long id;
    @Schema(description = "User name", example = "John Doe")
    @NotNull(message = "Name must not be null", groups = {OnUpdate.class, OnUpdate.class})
    @Length(max = 255, message = "Name must not exceed 255 characters", groups = {OnUpdate.class, OnUpdate.class})
    private String name;
    @Schema(description = "User email", example = "johndoe@gmail.com")
    @NotNull(message = "Username must not be null", groups = {OnUpdate.class, OnUpdate.class})
    @Length(max = 255, message = "Username must not exceed 255 characters", groups = {OnUpdate.class, OnUpdate.class})
    private String username;
    @Schema(description = "User crypted password")
    @NotNull(message = "Password must not be null", groups = {OnUpdate.class, OnCreate.class})
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @Schema(description = "User password confirmation")
    @NotNull(message = "Password confirmation must not be null", groups = OnCreate.class)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String passwordConfirmation;

}
