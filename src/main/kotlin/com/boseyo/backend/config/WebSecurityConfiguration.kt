package com.boseyo.backend.config

import com.boseyo.backend.jwt.JwtSecurityConfig
import com.boseyo.backend.jwt.JwtTokenProvider
import org.apache.catalina.filters.CorsFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig(private val tokenProvider: JwtTokenProvider, private val jwtAuthenticationEntryPoint: AuthenticationEntryPoint, private val jwtAccessDeniedHandler: AccessDeniedHandler) {
    @Bean
    fun passwordEncoder(): PasswordEncoder{
        return BCryptPasswordEncoder()
    }
        @Bean
        fun corsConfigurationSource(): CorsConfigurationSource {
                val configuration = CorsConfiguration()
        //        configuration.allowedOrigins = listOf("https://ssda.dawoony.com", "http://localhost:3000")
                configuration.allowCredentials
                configuration.addAllowedOriginPattern("*")
                configuration.addAllowedHeader("*")
                configuration.addAllowedMethod("*")
        //        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE")
                val source = UrlBasedCorsConfigurationSource()
                source.registerCorsConfiguration("/**", configuration)
                return source
           }
    @Bean
    fun configure(http: HttpSecurity): SecurityFilterChain {
        http
                .csrf().disable()

                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                // 세션을 사용하지 않기 때문에 STATELESS로 설정
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeHttpRequests()
                .requestMatchers("/api/signup", "/api/authenticate", "/api/confirm-email").permitAll()
                .anyRequest().authenticated()

                .and()
                .apply(JwtSecurityConfig(tokenProvider))

        return http.build()
    }
}


