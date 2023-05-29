package com.boseyo.backend.service

import com.boseyo.backend.dto.MakeDto
import com.boseyo.backend.entity.MakeDrawEntity
import com.boseyo.backend.repository.MakeRepository
import com.boseyo.backend.repository.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class MakeService(
    private val makeRepository: MakeRepository,
    private val userRepository: UserRepository
) {
    fun draw(makeDto: MakeDto): MakeDto{
        val drawer = SecurityContextHolder.getContext().authentication.principal as UserDetails
        println(drawer.username)
        val makeDrawEntity = MakeDrawEntity(
            user = userRepository.findOneWithAuthoritiesByUsername(drawer.username).orElse(null)!!,
            fontName = makeDto.fontName,
            imageBase64 = makeDto.imageBase64
        )
        makeRepository.save(makeDrawEntity)
        // TODO: ML 서버로 imageBase64 전송
        return MakeDto(
            imageBase64 = makeDto.imageBase64,
            fontName = makeDto.fontName
        )
    }
}