package com.tim.beatham.pubgolf.configuration

import com.tim.beatham.pubgolf.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtTokenFilter : OncePerRequestFilter() {

    @Autowired
    private lateinit var jwtTokenUtil: JwtTokenUtil

    @Autowired
    private lateinit var userRepository: UserRepository

    companion object {
        const val BEARER_HEADER = "Bearer"
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val header = request.getHeader(HttpHeaders.AUTHORIZATION)

        if (!checkHeader(header)) {
            filterChain.doFilter(request, response)
            return
        }

        val token = header.split(" ")[1].trim()

        if (!jwtTokenUtil.validate(token)) {
            filterChain.doFilter(request, response)
            return
        }

        setWebAuthenticationDetails(token, request)

        filterChain.doFilter(request, response)
    }

    private fun setWebAuthenticationDetails(token: String, request: HttpServletRequest) {
        val userDetails = userRepository.findById(jwtTokenUtil.getId(token)).orElse(null)
        val authenticationToken = UsernamePasswordAuthenticationToken(userDetails,
            null, mutableListOf())

        authenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
        SecurityContextHolder.getContext().authentication = authenticationToken
    }

    private fun checkHeader(header: String?): Boolean {
        return !header.isNullOrEmpty() && header.startsWith(BEARER_HEADER)
    }
}