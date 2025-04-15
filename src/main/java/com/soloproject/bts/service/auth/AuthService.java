package com.soloproject.bts.service.auth;

import com.soloproject.bts.dto.request.users.LoginRequest;
import com.soloproject.bts.dto.request.users.RegisterRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<Object> login(LoginRequest request);
    ResponseEntity<Object> register(RegisterRequest request);
}
