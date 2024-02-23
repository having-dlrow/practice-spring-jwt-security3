package com.example.demo.Controller;

import com.example.demo.Member.Member;
import com.example.demo.Member.MemberRepository;
import com.example.demo.Member.Role;
import com.example.demo.config.auth.PrincipalDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
public class HelloController {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final ObjectMapper objectMapper;

    @GetMapping("/")
    public String greeting() {
        return "Hello, World";
    }

    @GetMapping("/user")
    public String user(Authentication authentication) {
        try {
            PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
            System.out.println("principal : " + principal.getUser().getId());
            System.out.println("principal : " + principal.getUser().getPassword());
            System.out.println("principal : " + principal.getUser().getUsername());

            return objectMapper.writeValueAsString(principal);
        } catch (NullPointerException e) {
            return "User not authenticated";
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    // 어드민이 접근 가능
    @GetMapping("admin/users")
    public List<Member> users() {
        return memberRepository.findAll();
    }

    @PostMapping("join")
    public String join(@RequestBody Member user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of(Role.ADMIN));
        memberRepository.save(user);
        return "회원가입완료";
    }
}
