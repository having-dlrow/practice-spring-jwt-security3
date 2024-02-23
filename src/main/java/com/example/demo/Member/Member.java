package com.example.demo.Member;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    String username;

    String password;

    @Enumerated(EnumType.STRING)
    private List<Role> roles;

    public List<Role> getRoleList() {
        return roles;
    }
}
