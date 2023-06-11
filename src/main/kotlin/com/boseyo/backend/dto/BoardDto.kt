package com.boseyo.backend.dto

import com.boseyo.backend.entity.BoardEntity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime


data class BoardDto(
    var title: String,
    var fontName: String,
    var fontGenerator: String,
    val fontCreatedDate: LocalDateTime = LocalDateTime.now(),
    val makeDto: MakeDto? = null // MakeDto를 포함하는 필드 추가
) {
    fun toEntity(): BoardEntity {
        return BoardEntity(
            title = title,
            fontName = fontName,
            fontGenerator = fontGenerator,
            fontCreatedDate = fontCreatedDate,
            imageFile = makeDto?.imageFile // MakeDto의 imageFile을 BoardEntity의 imageFile로 매핑
        )
    }
}





