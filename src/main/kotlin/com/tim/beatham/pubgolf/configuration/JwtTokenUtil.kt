package com.tim.beatham.pubgolf.configuration

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.lang.Exception
import java.util.*

@Component
class JwtTokenUtil(val secret : String = "9cd09543ad753cc6e370023e6daed19d12093cb5fc79f69dbd1f7f3bbb9570939cd09543ad753cc6e370023e6daed19d12093cb5fc79f69dbd1f7f3bbb957093") {
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
                .parseClaimsJws(token)
        } catch (e: Exception) {
            println(e)
            return false
        }

        return true
    }

    fun getId(token: String): String {
        return Jwts.parserBuilder()
            .setSigningKey(secret)
            .build()
            .parseClaimsJws(token)
            .body.subject.split(" ")[0]
    }
}