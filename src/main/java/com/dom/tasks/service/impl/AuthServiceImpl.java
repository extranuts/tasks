package com.dom.tasks.service.impl;

import com.dom.tasks.service.AuthService;
import com.dom.tasks.web.dto.auth.JwtRequest;
import com.dom.tasks.web.dto.auth.JwtResponse;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public JwtResponse login(JwtRequest request) {
        return null;
    }

    @Override
    public JwtResponse refresh(String refreshToken) {
        return null;
    }
}
