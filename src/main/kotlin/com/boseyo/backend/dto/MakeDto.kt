package com.boseyo.backend.dto

import org.springframework.web.multipart.MultipartFile
import java.sql.Blob

class MakeDto (
    val image64: Blob? = null,
    val fontName: String? = null
)