package com.boseyo.backend.dto

import com.boseyo.backend.entity.UserEntity
import org.jetbrains.annotations.NotNull

data class UserDto(
        @field:NotNull
        var username: String? = null,

        @field:NotNull
        var email: String? = null,

        @field:NotNull
        var password: String? = null,

        var authorityDtoSet: Set<AuthorityDto>? = null
) {
    companion object {
        fun from(user: UserEntity): UserDto {
            return user.run {
                UserDto(
                        username = username,
                        authorityDtoSet = user.authorities!!
                                .map { authority ->
                                    AuthorityDto(authority.authorityName)
                                }
                                .toSet()
                )
            }
        }
    }
}