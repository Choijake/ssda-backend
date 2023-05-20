package com.boseyo.backend.repository

import com.boseyo.backend.entity.FileEntity
import org.springframework.data.jpa.repository.JpaRepository


interface FileRepository : JpaRepository<FileEntity, Long> {}