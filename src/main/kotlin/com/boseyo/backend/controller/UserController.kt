package com.boseyo.backend.controller

import com.boseyo.backend.dto.UserDto
import com.boseyo.backend.service.UserService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.io.IOException

@RestController
@RequestMapping("/api")
class UserController(
        private val userService: UserService
) {
    @PostMapping("/test-redirect")
    @Throws(IOException::class)
    fun testRedirect(response: HttpServletResponse) {
        response.sendRedirect("/api/user")
    }

    @PostMapping("/signup")
    fun signup(@RequestBody @Valid userDto: UserDto): ResponseEntity<UserDto> {
        return ResponseEntity.ok(userService.signup(userDto))
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    fun getMyUserInfo(request: HttpServletRequest): ResponseEntity<UserDto> {
        return ResponseEntity.ok(userService.myUserWithAuthorities)
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    fun getUserInfo(@PathVariable username: String): ResponseEntity<UserDto> {
        return ResponseEntity.ok(userService.getUserWithAuthorities(username))
    }

    @GetMapping("/signup/check")
    fun checkEmail(@RequestParam type: String, @RequestParam value: String): ResponseEntity<Boolean> {
        return when (type) {
            "email" -> ResponseEntity.ok(userService.checkEmail(value))
            "username" -> ResponseEntity.ok(userService.checkUsername(value))
            else -> ResponseEntity.badRequest().build()
        }
    }


}