package com.tim.beatham.pubgolf.configuration

import com.tim.beatham.pubgolf.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.stereotype.Component
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtTokenFilter(manager: JwtAuthenticationManager) : BasicAuthenticationFilter(manager) {

    @Autowired
    private lateinit var jwtTokenUtil: JwtTokenUtil

    @Autowired
    private lateinit var userRepository: UserRepository

    companion object {
        const val BEARER_HEADER = "Bearer"
    }

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val authentication = parseTokenRequest(request)

        if (authentication == null) {
            SecurityContextHolder.clearContext()
        } else {
            SecurityContextHolder.getContext().authentication = authentication
        }

        chain.doFilter(request, response)
    }

    private fun parseTokenRequest(
        request: HttpServletRequest
    ): UsernamePasswordAuthenticationToken? {
        val header = request.getHeader(HttpHeaders.AUTHORIZATION)

        if (!checkHeader(header))
            return null

        val token = header.split(" ")[1].trim()

        if (!jwtTokenUtil.validate(token))
            return null

        return getWebAuthenticationDetails(token, request)
    }

    private fun getWebAuthenticationDetails(token: String, request: HttpServletRequest): UsernamePasswordAuthenticationToken? {
        val userDetails = userRepository.findById(jwtTokenUtil.getId(token)).orElse(null) ?: return null

        val authenticationToken = UsernamePasswordAuthenticationToken(userDetails,
            null, mutableListOf())

        authenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
        return authenticationToken
    }

    private fun checkHeader(header: String?): Boolean {
        return !header.isNullOrEmpty() && header.startsWith(BEARER_HEADER)
    }
}