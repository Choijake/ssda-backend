package com.boseyo.backend.dto

data class MakeDto(
    val imageBase64: String? = null,
    val contentType: String? = null,
    val fontName: String? = null,
    val imageFile: String? = null // 이미지 파일의 경로를 저장 필드
)
