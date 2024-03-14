package com.example.driverservice.security;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static com.example.driverservice.security.SecurityConstants.ACTUATOR;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {


    private final JwtAuthConverter jwtAuthConverter;

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .oauth2ResourceServer(oauth
                        -> oauth.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter))
                )

                .authorizeHttpRequests(request -> request
                        .requestMatchers(HttpMethod.GET, ACTUATOR)
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
        ;
        return http.build();
    }

}
