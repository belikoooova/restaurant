package com.example.app.auth.dto;

import com.example.app.auth.data.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private Role role;
}
