package com.boseyo.backend.dto

import org.springframework.web.multipart.MultipartFile

class MakeDto (
    val image64: MultipartFile? = null,
    val fontName: String? = null
)