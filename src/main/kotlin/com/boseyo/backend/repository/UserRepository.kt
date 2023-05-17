package com.boseyo.backend.repository

import com.boseyo.backend.entity.UserEntity
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<UserEntity?, Long?> {
    @EntityGraph(attributePaths = ["authorities"])
    fun findOneWithAuthoritiesByUsername(username: String): Optional<UserEntity>

    fun findByEmail(email: String): UserEntity
}