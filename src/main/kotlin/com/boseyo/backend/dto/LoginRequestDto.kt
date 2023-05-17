package com.boseyo.backend.dto

import lombok.Data
import org.jetbrains.annotations.NotNull


@Data
class LoginRequestDto {
    @field:NotNull
    var username: String? = null
    @field:NotNull
    var password: String? = null
}