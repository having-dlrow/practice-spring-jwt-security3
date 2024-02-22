package com.example.demo.config.jwt;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String username;
    private String password;
}