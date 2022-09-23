package com.tim.beatham.pubgolf.configuration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class JwtAuthenticationManager : AuthenticationManager {

    @Autowired
    private lateinit var passwordEncoder: BCryptPasswordEncoder

    @Autowired
    private lateinit var jwtUserDetailsService: JwtUserDetailsService

    override fun authenticate(authentication: Authentication): Authentication {
        val userDetail = jwtUserDetailsService.loadUserByUsername(authentication.name)

        if (!passwordEncoder.matches(authentication.credentials.toString(), userDetail.password)) {
            throw BadCredentialsException("Authorization error")
        }

        return UsernamePasswordAuthenticationToken(userDetail, userDetail.password)
    }
}