package com.boseyo.backend.dto

import jakarta.validation.constraints.NotNull

data class ConfirmEmailDto(
    @field:NotNull
    var email: String? = null,
    @field:NotNull
    var token: String? = null
)