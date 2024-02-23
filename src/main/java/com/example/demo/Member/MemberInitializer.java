package com.example.demo.Member;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberInitializer {

    private final PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;

    @PostConstruct
    public void addMember(){
        Member user = new Member();
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("p@ssword"));
        user.setRoles("ROLE_USER");

        memberRepository.save(user);

        user = new Member();
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("p@ssword"));
        user.setRoles("ROLE_ADMIN");

        memberRepository.save(user);


    }
}
