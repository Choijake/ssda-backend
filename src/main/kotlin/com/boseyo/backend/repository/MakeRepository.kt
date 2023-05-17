package com.boseyo.backend.repository

import com.boseyo.backend.entity.MakeDrawEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MakeRepository: JpaRepository<MakeDrawEntity, Long>{
    fun findByFontName(fontName: String): MakeDrawEntity?
}