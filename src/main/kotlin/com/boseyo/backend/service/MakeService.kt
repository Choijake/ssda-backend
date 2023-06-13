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
import org.springframework.web.util.UriComponentsBuilder


@Service
class MakeService(
    private val makeRepository: MakeRepository,
    private val userRepository: UserRepository
) {
    fun draw(makeDto: MakeDto): String {
        val drawer = SecurityContextHolder.getContext().authentication.principal as UserDetails
        val makeDrawEntity = MakeDrawEntity(
            user = userRepository.findOneWithAuthoritiesByUsername(drawer.username).orElse(null)!!,
            fontName = makeDto.fontName,
            image64 = makeDto.imageBase64,
            contentType = makeDto.contentType
        )
        makeRepository.save(makeDrawEntity)

        return requestToMl(makeDto, drawer.username)
    }

    @Suppress("CAST_NEVER_SUCCEEDS")
    fun requestToMl(makeDto: MakeDto, username: String): String {
        val reqUri = UriComponentsBuilder.newInstance()
            .scheme("http")
            .host("117.16.94.218")
            .port(8000)
            .path("/font_generation/images")
            .pathSegment(username, makeDto.fontName)
            .build()
            .toUriString()
        val restTemplate = RestTemplate()
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        return try {
            val response: ResponseEntity<String> = restTemplate.postForObject(
                reqUri, HttpEntity(makeDto.imageBase64?.let { MLRequest(it) }, headers),
                String::class.java
            ) as ResponseEntity<String>
            response.body.toString()
        } catch (e: Exception) {
            ObjectMapper().writeValueAsString(e.message)
        }
    }
}
data class MLRequest(val imagebase64: String)