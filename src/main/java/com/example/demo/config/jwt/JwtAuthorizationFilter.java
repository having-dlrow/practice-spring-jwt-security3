package com.example.demo.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.Member.Member;
import com.example.demo.Member.MemberRepository;
import com.example.demo.config.auth.PrincipalDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

// 인가
@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private MemberRepository userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, MemberRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String header = request.getHeader(JwtProperties.HEADER_STRING);
        if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        log.info("header : " + header);

        // token 생성
        String token = header.replace(JwtProperties.TOKEN_PREFIX, "");
        String username = JWT
                .require(Algorithm.HMAC512(JwtProperties.SECRET))
                .build()
                .verify(token)
                .getClaim("username")
                .asString();


        if (username != null) {

            // user 조회
            Member user = userRepository.findByUsername(username);
            if (user != null) {

                // 1. 토큰 검증
                // 인증은 토큰 검증
                PrincipalDetails principalDetails = new PrincipalDetails(user);
                // 2. 토큰 생성 (Authentication 객체)
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        principalDetails, // 나중에 컨트롤러에서 DI해서 쓸 때 사용하기 편함.
                        null, // 패스워드는 모르니까 null 처리, 어차피 지금 인증하는게 아니니까!!
                        principalDetails.getAuthorities());

                // 3. 강제로 시큐리티의 세션에 접근하여 값 저장
                // 이때, UserDetailsService@loadByUsername 호출 됨.
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(request, response);
    }

}
