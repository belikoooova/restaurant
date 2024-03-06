package com.example.app.auth.service;

import com.example.app.auth.dto.LoginRequest;
import com.example.app.auth.dto.RegisterRequest;

public interface AuthenticationService {
    void register(RegisterRequest request);
    void login(LoginRequest request);
}
