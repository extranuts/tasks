package com.dom.tasks.service;

import com.dom.tasks.web.dto.auth.JwtRequest;
import com.dom.tasks.web.dto.auth.JwtResponse;

public interface AuthService {

    JwtResponse login(JwtRequest request);

    JwtResponse refresh(String refreshToken);


}
