package com.hnust.zsg.entity.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MyUserVO implements Serializable, UserDetails {
    private static final long serialVersionUID=887L;
    private Long id;
    private String username;
    private String password;
    private String email;
    private Boolean enabled;
    private String verifyCode;

    private List<SimpleGrantedAuthority> authorities;
    @JsonIgnoreProperties
    private static Boolean accountNonExpired;
    private static Boolean accountNonLocked;

    private static Boolean credentialsNonExpired;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;

    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
