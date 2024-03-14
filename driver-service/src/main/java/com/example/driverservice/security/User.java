package com.example.driverservice.security;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import static com.example.driverservice.security.SecurityConstants.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails, OAuth2User {

    private UUID id;
    private String username;
    private String phone;
    private String email;
    private String name;
    private String surname;
    private String carNumber;
    private String model;
    private String color;

    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Object getAttribute(String name) {
        return getAttributes().get(name);
    }

    @Override
    public Map<String, Object> getAttributes() {

        return Map.of("id", id,
                USER_NAME, username,
                EMAIL, email,
                PHONE, phone,
                NAME, name,
                SURNAME, surname,
                COLOR, color,
                CAR_NUMBER, carNumber,
                MODEL, model);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return username;
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
        return true;
    }

    @Override
    public String getName() {
        return null;
    }
}