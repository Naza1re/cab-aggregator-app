package org.example.carparkservice.security

import org.example.carparkservice.security.SecurityConstants.RESOURCES_ACCESS
import org.example.carparkservice.service.CarParkService
import org.springframework.core.convert.converter.Converter
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import org.springframework.stereotype.Component

@Component
class JwtAuthConverter(
    private val carParkService: CarParkService,
    private val properties: JwtAuthConverterProperties
) : Converter<Jwt, AbstractAuthenticationToken> {

    private val jwtGrantedAuthoritiesConverter = JwtGrantedAuthoritiesConverter()

    override fun convert(jwt: Jwt): AbstractAuthenticationToken {
        val authorities = jwtGrantedAuthoritiesConverter.convert(jwt).orEmpty() +
                extractResourceRoles(jwt)
        val user = carParkService.extractUserInfo(jwt)
        val authToken = UsernamePasswordAuthenticationToken(
            user,
            null,
            authorities
        )
        SecurityContextHolder.getContext().authentication = authToken

        return authToken
    }

    private fun extractResourceRoles(jwt: Jwt): Collection<GrantedAuthority> {
        val resourceAccess = jwt.getClaim<Map<String, Any>>(RESOURCES_ACCESS)
        val resource = resourceAccess?.get(properties.resourceId) as? Map<String, Any>?
        val resourceRoles = resource?.get(SecurityConstants.ROLES) as? Collection<String>

        return resourceRoles?.map { SimpleGrantedAuthority(it) }.orEmpty()
    }
}