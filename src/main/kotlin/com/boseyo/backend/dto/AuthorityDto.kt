package com.boseyo.backend.dto

import com.boseyo.backend.entity.Authority

data class AuthorityDto(
        var authorityName: String? = null
){
    companion object {
        fun from(authority: Authority): AuthorityDto {
            return authority.run {
                AuthorityDto(
                        authorityName = authorityName
                )
            }
        }
    }
}