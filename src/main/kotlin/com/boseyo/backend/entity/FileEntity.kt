package com.boseyo.backend.entity

import jakarta.persistence.*
import lombok.Getter

@Getter
@Entity
data class FileEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(nullable = false)
    var ogFilename:String,

    @Column(nullable = false)
    var fileName:String,

    @Column(nullable=false)
    var filePath:String,
){
    fun getId(): Long {
        return id
    }
}