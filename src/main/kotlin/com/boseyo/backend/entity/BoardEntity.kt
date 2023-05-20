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

/*
폰트이름
제작자
폰트 이미지?예시
타이핑 할 수 있는거
좋아요?
 */