package com.tim.beatham.pubgolf.configuration

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.lang.Exception
import java.util.*

@Component
class JwtTokenUtil(val secret : String = "secret123") {
    companion object {
        const val EXPIRE_DURATION = 24 * 60 * 60 * 1000
    }

    fun generateToken(user: UserDetails): String {
        return Jwts.builder()
            .setSubject("${user.username} ${user.password}")
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + EXPIRE_DURATION))
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact()
    }

    fun validate(token: String) : Boolean {
        try {
            Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJwt(token)
            return true
        } catch (e: Exception) {}

        return false;
    }

    fun getEmail(token: String): String {
        return Jwts.parserBuilder()
            .setSigningKey(secret)
            .build()
            .parseClaimsJwt(token)
            .body.subject.split(" ")[0]
    }
}