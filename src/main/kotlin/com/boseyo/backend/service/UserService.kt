package com.boseyo.backend.service

import com.boseyo.backend.Exception.DuplicateMemberException
import com.boseyo.backend.Exception.NotFoundMemberException
import com.boseyo.backend.dto.UserDto
import com.boseyo.backend.entity.Authority
import com.boseyo.backend.entity.UserEntity
import com.boseyo.backend.repository.UserRepository
import com.boseyo.backend.util.SecurityUtil
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
        private val userRepository: UserRepository,
        private val passwordEncoder: PasswordEncoder,
        private val emailService: EmailService
    ) {
    @Transactional
    fun signup(userDto: UserDto): UserDto {
        if (userRepository.findOneWithAuthoritiesByUsername(userDto.username!!).orElse(null) != null) {
            throw DuplicateMemberException("이미 가입되어 있는 유저입니다.")
        }

        val authority = Authority("ROLE_USER")

        val user = UserEntity(
                username = userDto.username,
                email = userDto.email,
                password = passwordEncoder.encode(userDto.password),
                authorities = setOf(authority),
                enabled = false
        )
        emailService.sendEmailForm(userDto.email!!)
        return UserDto.from(userRepository.save(user))
    }

    @Transactional(readOnly = true)
    fun getUserWithAuthorities(username: String): UserDto {
        return UserDto.from(
                userRepository.findOneWithAuthoritiesByUsername(username)
                        .orElse(null)
        )
    }

    @Transactional
    fun emailConfirm(email: String) {
        val user = userRepository.findByEmail(email) ?: throw NotFoundMemberException("Member not found")
        user.enabled = true
        userRepository.save(user)
    }

    @Transactional(readOnly = true)
    fun getUserWithAuthorities(): UserDto {
        return UserDto.from(
                userRepository.findOneWithAuthoritiesByUsername(SecurityUtil.currentUsername.get())
                        .orElseThrow {
                            throw NotFoundMemberException("Member not found")
                        }
        )
}

    @get:Transactional(readOnly = true)
    val myUserWithAuthorities: UserDto
        get() = UserDto.from(
                SecurityUtil.currentUsername
                        .flatMap {
                            username: String -> userRepository.findOneWithAuthoritiesByUsername(username)
                        }
                        .orElseThrow {
                            throw NotFoundMemberException("Member not found")
                        }
        )

    @Transactional(readOnly = true)
    fun checkEmail(email: String): Boolean {
        return userRepository.findByEmail(email) != null
    }

    @Transactional(readOnly = true)
    fun checkUsername(username: String): Boolean {
        return userRepository.findByUsername(username) != null
    }
}