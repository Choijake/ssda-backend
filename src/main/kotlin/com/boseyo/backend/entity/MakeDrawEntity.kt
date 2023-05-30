package com.boseyo.backend.entity

import jakarta.persistence.*
import java.sql.Blob

@Entity
class MakeDrawEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Lob
    @Column(name = "image64", nullable = false, columnDefinition = "BLOB")
    val image64: Blob? = null,
    val fontName: String? = null,

    @ManyToOne
    val user: UserEntity? = null
)