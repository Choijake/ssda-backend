package com.boseyo.backend.dto

import com.boseyo.backend.entity.BoardEntity
import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime


data class BoardDto(
    val id: Long? = null,

    var fontName:String,

    var fontGenerator:String,

    var fileId : Long,

    val fontCreatedDate: LocalDateTime = LocalDateTime.now()
){
    fun toEntity():BoardEntity{
        return BoardEntity(id, fontName, fontGenerator, fileId, fontCreatedDate)
    }
}