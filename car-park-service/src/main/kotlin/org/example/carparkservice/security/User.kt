package org.example.carparkservice.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.user.OAuth2User
import java.util.*

data class User(
    var id: UUID? = null,
    var username1: String? = null,
    var phone: String? = null,
    var email: String? = null,
    var name1: String? = null,
    var surname: String? = null,
    var carNumber: String? = null,
    var model: String? = null,
    var color: String? = null,
    private var authorities: Collection<GrantedAuthority>? = null
) : UserDetails, OAuth2User {

    override fun getAuthorities(): Collection<GrantedAuthority>? = authorities

    override fun getPassword(): String? = null

    override fun getUsername(): String? = username1

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true

    override fun getAttributes(): Map<String, Any?> = mapOf(
        "id" to id,
        SecurityConstants.USER_NAME to username,
        SecurityConstants.EMAIL to email,
        SecurityConstants.PHONE to phone,
        SecurityConstants.NAME to name,
        SecurityConstants.SURNAME to surname
    )

    // Реализация метода getName, возвращающего null
    override fun getName(): String? = null

    override fun <A> getAttribute(name: String): A? = getAttributes()[name] as? A
}
