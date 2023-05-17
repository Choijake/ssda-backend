package com.boseyo.backend.jwt

import com.boseyo.backend.dto.ConfirmEmailDto
import com.boseyo.backend.dto.LoginRequestDto
import com.boseyo.backend.dto.TokenDto
import com.boseyo.backend.jwt.JwtFilter
import com.boseyo.backend.jwt.JwtTokenProvider
import com.boseyo.backend.service.EmailService
import com.boseyo.backend.service.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class AuthController(
        private val tokenProvider: JwtTokenProvider,
        private val authenticationManagerBuilder: AuthenticationManagerBuilder,
        private val emailService: EmailService,
        private val userService: UserService
) {
    @PostMapping("/authenticate")
    fun authorize(@RequestBody @Valid loginDto: LoginRequestDto): ResponseEntity<TokenDto> {
        val authenticationToken = UsernamePasswordAuthenticationToken(loginDto.username, loginDto.password)
        val authentication: Authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken)
        SecurityContextHolder.getContext().setAuthentication(authentication)
        val jwt: String = tokenProvider.createToken(authentication)
        val httpHeaders = HttpHeaders()
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer $jwt")

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        val cookie = ResponseCookie.from("accessToken", jwt)
            .maxAge((7 * 24 * 60 * 60).toLong())
            .path("/")
            .secure(true)
            .sameSite("None")
            .httpOnly(true)
            .build()
        httpHeaders.add(HttpHeaders.SET_COOKIE, cookie.toString())
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        return ResponseEntity<TokenDto>(TokenDto(jwt), httpHeaders, HttpStatus.OK)
    }

    @PostMapping("/confirm-email")
    fun confirmEmail(@RequestBody confirmEmailDto: ConfirmEmailDto): ResponseEntity<String> {
        val confirmResult = emailService.confirmEmail(confirmEmailDto.email!!, confirmEmailDto.token!!)
        when (confirmResult.result)
        {
            false -> return ResponseEntity<String>(confirmResult.message, HttpStatus.BAD_REQUEST)
            true -> {
                userService.emailConfirm(confirmEmailDto.email!!)
                return ResponseEntity<String>(confirmResult.message, HttpStatus.OK)
            }
        }
    }
}