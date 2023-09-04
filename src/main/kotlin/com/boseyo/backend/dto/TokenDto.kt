package com.boseyo.backend.dto

data class TokenDto (
    var username: String? = null,
    var email: String? = null,
    var role: String? = null,
    var accessToken: String? = null,
    var refreshToken: String? = null
)