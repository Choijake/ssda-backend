package com.boseyo.backend.entity

import jakarta.persistence.*
import jakarta.persistence.Entity
import java.time.LocalDateTime


@Entity
data class BoardEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var title: String,

    @Column(length = 15, nullable = false)
    var fontName: String,

    @Column(length = 15, nullable = false)
    var fontGenerator: String,

    @Column(nullable = true)
    var imageFile: String? = null, // 이미지 파일의 경로를 저장 필드

    @Column(updatable = false, nullable = false)
    var fontCreatedDate: LocalDateTime = LocalDateTime.now()
)
