package com.boseyo.backend.entity

import jakarta.persistence.*

@Entity
class MakeDrawEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "image64", nullable = false, columnDefinition = "TEXT")
    val image64: String? = null,
    val fontName: String? = null,
    val contentType: String? = null,

    @ManyToOne
    val user: UserEntity? = null
)