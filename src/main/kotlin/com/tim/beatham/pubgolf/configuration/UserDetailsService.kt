package com.tim.beatham.pubgolf.configuration

import com.tim.beatham.pubgolf.controllers.exception.UserNotFoundException
import com.tim.beatham.pubgolf.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class JwtUserDetailsService : UserDetailsService {

    @Autowired
    private lateinit var userRepository: UserRepository

    override fun loadUserByUsername(email: String): UserDetails {
        return userRepository.findByEmail(email).orElseThrow { UserNotFoundException(email) }
    }
}