package com.boseyo.backend.entity

import jakarta.persistence.*
import org.springframework.web.multipart.MultipartFile

@Entity
class MakeDrawEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Lob
    @Column(name = "image64", nullable = false, columnDefinition = "BLOB")
    val image64: MultipartFile? = null,
    val fontName: String? = null,

    @ManyToOne
    val user: UserEntity? = null
)