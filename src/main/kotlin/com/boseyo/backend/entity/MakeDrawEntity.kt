package com.boseyo.backend.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

@Entity
class MakeDrawEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    val imageBase64: String? = null,
    val fontName: String? = null,

    @ManyToOne
    val user: UserEntity? = null
)