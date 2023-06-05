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
    val fontCreatedDate: LocalDateTime = LocalDateTime.now()
) {
    fun toEntity(): BoardEntity {
        return BoardEntity(
            title = title,
            fontName = fontName,
            fontGenerator = fontGenerator,
            fontCreatedDate = fontCreatedDate
        )
    }
}
