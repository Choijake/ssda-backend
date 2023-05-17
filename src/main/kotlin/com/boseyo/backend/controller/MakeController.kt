package com.boseyo.backend.controller

import com.boseyo.backend.dto.MakeDto
import com.boseyo.backend.service.MakeService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/make")
class MakeController(private val makeService: MakeService) {
    @PostMapping("/draw")
    @PreAuthorize("hasAnyRole('USER')")
    fun draw(@RequestBody makeDto: MakeDto): ResponseEntity<String> {
        return ResponseEntity.ok(makeService.draw(makeDto))
    }
}