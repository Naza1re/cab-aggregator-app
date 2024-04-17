package org.example.pricecalculatorservice.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig(private val jwtAuthConverter: JwtAuthConverter) {

    @Bean
    @Throws(Exception::class)
    fun defaultSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .oauth2ResourceServer { oauth -> oauth.jwt { jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter) } }
            .authorizeRequests { requests ->
                requests
                    .requestMatchers(HttpMethod.GET, SecurityConstants.ACTUATOR).permitAll()
                    .anyRequest().authenticated()
            }
        return http.build()
    }
}