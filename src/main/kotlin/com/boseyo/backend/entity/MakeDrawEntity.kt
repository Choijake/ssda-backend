package com.boseyo.backend.entity

import jakarta.persistence.*

@Entity
class MakeDrawEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Lob
    @Column(name = "image_base64", nullable = false, columnDefinition = "BLOB")
    val imageBase64: ByteArray? = null,
    val fontName: String? = null,

    @ManyToOne
    val user: UserEntity? = null
)