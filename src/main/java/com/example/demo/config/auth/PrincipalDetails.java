package com.example.demo.config.auth;

import com.example.demo.Member.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class PrincipalDetails implements UserDetails {

    private Member user;

    public PrincipalDetails(Member user) {
        this.user = user;
    }

    public Member getUser() {
        return user;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        // 계정 만료 여부
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // 계정 잠김 여부
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        //  credentials(password) 만료 여부
        return true;
    }

    @Override
    public boolean isEnabled() {
        //  유저 사용 가능 여부
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {


//        List<Role> roles = member.getRoles();
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        for (Role role : roles) {
//            authorities.add(new SimpleGrantedAuthority(role.getRole()));
//        }
//        return authorities;

//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        member.getRoles.forEach (r -> {
//            authorities.add(()->{
//              new SimpleGrantedAuthority(role.getRole()
//            });
//        });

        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        user.getRoleList().forEach(r -> {
            authorities.add(() -> {
                return r;
            });
        });
        return authorities;
    }
}
