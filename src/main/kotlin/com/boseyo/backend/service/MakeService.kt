package com.boseyo.backend.service

import com.boseyo.backend.dto.MakeDto
import com.boseyo.backend.entity.MakeDrawEntity
import com.boseyo.backend.repository.MakeRepository
import com.boseyo.backend.repository.UserRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate


@Service
class MakeService(
    private val makeRepository: MakeRepository,
    private val userRepository: UserRepository
) {
    fun draw(makeDto: MakeDto): String {
        val objectMapper = ObjectMapper()
        val drawer = SecurityContextHolder.getContext().authentication.principal as UserDetails
        val makeDrawEntity = MakeDrawEntity(
            user = userRepository.findOneWithAuthoritiesByUsername(drawer.username).orElse(null)!!,
            fontName = makeDto.fontName,
            image64 = makeDto.imageBase64,
            contentType = makeDto.contentType
        )
        makeRepository.save(makeDrawEntity)
        val uri = "http://117.16.94.218:8000/font_generation_with_total_image/test"
        val restTemplate = RestTemplate()
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        val response: ResponseEntity<String> = restTemplate.postForObject<String>(
            uri, HttpEntity(makeDto.imageBase64?.let { MLRequest(it) },headers),
            String::class.java
        ) as ResponseEntity<String>
        println(response?.statusCode)
        return drawer.toString()
    }
}
data class MLRequest(
    val imagebase64: String
)