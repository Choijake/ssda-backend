package com.boseyo.backend.jwt

import com.boseyo.backend.dto.LoginRequestDto
import com.boseyo.backend.dto.TokenDto
import com.boseyo.backend.jwt.JwtFilter
import com.boseyo.backend.jwt.JwtTokenProvider
import jakarta.validation.Valid
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
@RestController
@RequestMapping("/api")
class AuthController(
    private val tokenProvider: JwtTokenProvider,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder
) {
    @PostMapping("/authenticate")
    fun authorize(@RequestBody @Valid loginDto: LoginRequestDto): ResponseEntity<TokenDto> {
        val authenticationToken = UsernamePasswordAuthenticationToken(loginDto.username, loginDto.password)
        val authentication: Authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken)
        SecurityContextHolder.getContext().setAuthentication(authentication)
        val refreshToken: String = tokenProvider.createRefreshToken()
        val accessToken: String = tokenProvider.createAccessToken(authentication)
        val httpHeaders = HttpHeaders()

        // accessToken을 "Authorization" 헤더에 추가
        httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer $accessToken")

        // refreshToken을 쿠키로 전달
        val cookie = ResponseCookie.from("refreshToken", refreshToken)
            .maxAge((7 * 24 * 60 * 60).toLong())
            .path("/")
            .domain("ssda.dawoony.com")
            .secure(true)
            .sameSite("None")
            .httpOnly(true)
            .build()
        httpHeaders.add(HttpHeaders.SET_COOKIE, cookie.toString())

        return ResponseEntity<TokenDto>(TokenDto(accessToken), httpHeaders, HttpStatus.OK)
    }
}
