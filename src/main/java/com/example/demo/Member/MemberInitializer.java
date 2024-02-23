package com.example.demo.Member;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MemberInitializer {

    private final PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;

    @PostConstruct
    public void addMember() {
        Member user = new Member();
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("p@ssword"));
        user.setRoles(List.of(Role.USER));

        memberRepository.save(user);

        user = new Member();
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("p@ssword"));
        user.setRoles(List.of(Role.ADMIN));

        memberRepository.save(user);
    }
}
