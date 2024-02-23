package com.example.demo.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.config.auth.PrincipalDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import java.io.IOException;

// 인가
@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final PrincipalDetailsService principalDetailsService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, PrincipalDetailsService principalDetailsService) {
        super(authenticationManager);
        this.principalDetailsService = principalDetailsService;
    }

    // 공부 용이니까, 로직 나누지 않고 한줄로 구현, 나중에는 3개 정도로 나눠야 할듯.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String bearerToken = request.getHeader(JwtProperties.HEADER_STRING);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtProperties.TOKEN_PREFIX)) {
            String jwtToken = bearerToken .substring(7);
            log.info("header : " + jwtToken);

            // token 생성
            String username = JWT
                    .require(Algorithm.HMAC512(JwtProperties.SECRET))
                    .build()
                    .verify(jwtToken)
                    .getClaim("username")
                    .asString();

            // 1. 토큰 검증
            // 인증은 토큰 검증
            UserDetails userDetails = principalDetailsService.loadUserByUsername(username);
            if(userDetails.getUsername() != null ) {
                // 2. 토큰 생성 (Authentication 객체)
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        userDetails.getPassword(),
                        userDetails.getAuthorities());

                // 3. 강제로 시큐리티의 세션에 접근하여 값 저장
                // 이때, UserDetailsService@loadByUsername 호출 됨.
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(request, response);
    }

}
