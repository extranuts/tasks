package com.dom.tasks.web.controller;

import com.dom.tasks.domain.user.User;
import com.dom.tasks.service.AuthService;
import com.dom.tasks.service.UserService;
import com.dom.tasks.web.dto.auth.JwtRequest;
import com.dom.tasks.web.dto.auth.JwtResponse;
import com.dom.tasks.web.dto.user.UserDto;
import com.dom.tasks.web.dto.validation.OnCreate;
import com.dom.tasks.web.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    private final UserMapper userMapper;

    @PostMapping("/login")
    public JwtResponse login(@Validated @RequestBody JwtRequest request) {
        return authService.login(request);
    }

    @PostMapping("/register")
    public UserDto register(@Validated(OnCreate.class) @RequestBody UserDto userDto) {
        User createdUser = userService.create(userMapper.toEntity(userDto));
        return userMapper.userToDto(createdUser);
    }

    @PostMapping("/refresh")
    public JwtResponse refresh(@RequestBody String refreshToken) {
        return authService.refresh(refreshToken);
    }

}
