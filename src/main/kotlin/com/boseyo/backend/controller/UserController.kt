package com.boseyo.backend.controller

import com.boseyo.backend.dto.UserDto
import com.boseyo.backend.service.UserService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
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
}

//import com.boseyo.backend.config.auth.PrincipalDetails
//import com.boseyo.backend.config.auth.PrincipalDetailsService
//import com.boseyo.backend.entity.UserEntity
//import com.boseyo.backend.repository.UserRepository
//import lombok.RequiredArgsConstructor
//import org.springframework.security.core.Authentication
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.security.crypto.factory.PasswordEncoderFactories
//import org.springframework.security.crypto.password.PasswordEncoder
//import org.springframework.web.bind.annotation.*
//
//
////@CrossOrigin
//@RestController
//@RequestMapping("/api/user")
//@RequiredArgsConstructor
//class UserController(@Autowired val principalDetailsService: PrincipalDetailsService, @Autowired val userRepository: UserRepository) {
//
//    val passwordEncoder: PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()
//
//    // Tip : JWT를 사용하면 UserDetailsService를 호출하지 않기 때문에 @AuthenticationPrincipal 사용 불가능.
//    // 왜냐하면 @AuthenticationPrincipal은 UserDetailsService에서 리턴될 때 만들어지기 때문이다.
//    // 유저 혹은 매니저 혹은 어드민이 접근 가능
//    @GetMapping("/cert")
//    fun user(authentication: Authentication): String {
//        val principal = authentication.
//        principal as PrincipalDetails
//        println("principal : " + principal.getUser().id)
//        println("principal : " + principal.getUser().username)
//        println("principal : " + principal.getUser().password)
//        return "<h1>user</h1>"
//    }
//
//    // 매니저 혹은 어드민이 접근 가능
//    @GetMapping("/manager/reports")
//    fun reports(): String {
//        return "<h1>reports</h1>"
//    }
//
//    // 어드민이 접근 가능
//    @GetMapping("/admin/users")
//    fun users(): List<UserEntity> {
//        return userRepository.findAll()
//    }
//
//    @PostMapping("/join")
//    fun join(@RequestBody joinRequestDto: JoinRequestDto): String {
//        principalDetailsService.join(joinRequestDto.username, passwordEncoder.encode(joinRequestDto.password), joinRequestDto.email, "ROLE_USER")
//        return "회원가입완료"
//    }
//}
//
//data class JoinRequestDto(
//    var username: String,
//    var password: String,
//    var email: String
//)
//
//
