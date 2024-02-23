package com.example.demo.config.jwt;

import org.springframework.beans.factory.annotation.Value;

public class JwtProperties {

    @Value("${jwt.secret}")
    static String SECRET;

    @Value("${jwt.expiration}")
    static int EXPIRATION_TIME;

    static String TOKEN_PREFIX = "Bearer ";
    static String HEADER_STRING = "Authorization";
}
