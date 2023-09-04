package com.boseyo.backend.dto

data class TokenDto (
    var token: String? = null,
    var username: String? = null,
    var role: String? = null
)