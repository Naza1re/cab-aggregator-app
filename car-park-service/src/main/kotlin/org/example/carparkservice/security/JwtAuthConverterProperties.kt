package org.example.carparkservice.security

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.validation.annotation.Validated

@Configuration
@ConfigurationProperties(prefix = "jwt.auth.converter")
@Validated
data class JwtAuthConverterProperties(
    var resourceId: String? = null,
    var principalAttribute: String? = null
)