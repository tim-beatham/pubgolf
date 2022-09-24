package com.tim.beatham.pubgolf.configuration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
@EnableWebSecurity
class SecurityConfiguration(private val authException: AuthException) {

    @Autowired
    private lateinit var jwtTokenFilter: JwtTokenFilter

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer {
            web ->
                web.ignoring().antMatchers("/api/users/login", "/api/users/register")
        }
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.cors().and().csrf().disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .anyRequest().authenticated().and()
            .addFilter(jwtTokenFilter)
            .exceptionHandling()
            .authenticationEntryPoint(authException)

        return http.build()
    }

    @Bean
    fun corsFilter(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowCredentials = true
        config.addAllowedOrigin("*")
        config.addAllowedHeader("*")
        config.addAllowedMethod("*")
        source.registerCorsConfiguration("/**", config)
        return CorsFilter(source)
    }
}