package com.boseyo.backend.entity

import jakarta.persistence.*
import lombok.Data
import java.time.LocalDateTime
import java.util.*


@Entity
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    val user_id: Long? = null,
    var email: String?= null,
    var password: String? = null,
    var username: String? = null,
    var enabled: Boolean = false,
//    var created: LocalDateTime,
//    var updated: LocalDateTime,
//    var deleted: LocalDateTime? = null,


    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "user_id")],
            inverseJoinColumns = [JoinColumn(name = "authority_name", referencedColumnName = "authority_name")]
    )
    val authorities: Set<Authority>? = null
)