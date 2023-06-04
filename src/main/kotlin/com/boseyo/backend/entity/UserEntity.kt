package com.boseyo.backend.entity

import jakarta.persistence.*


@Entity
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    val user_id: Long? = null,
    @Column(unique = true)
    var email: String?= null,
    var password: String? = null,
    var username: String? = null,
    var enabled: Boolean = false,

    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "user_id")],
            inverseJoinColumns = [JoinColumn(name = "authority_name", referencedColumnName = "authority_name")]
    )
    val authorities: Set<Authority>? = null
)