package com.boseyo.backend.entity

import jakarta.persistence.*
import jakarta.persistence.Entity
import java.time.LocalDateTime


@Entity
data class BoardEntity (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(length= 15, nullable = false)
    var fontName:String,

    @Column(length = 15, nullable = false)
    var fontGenerator:String,

    @Column
    var fileId : Long,

    @Column(updatable = false, nullable = false)
    val fontCreatedDate: LocalDateTime = LocalDateTime.now()
)
