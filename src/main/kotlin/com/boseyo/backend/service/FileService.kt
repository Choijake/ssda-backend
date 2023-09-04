//package com.boseyo.backend.service
//
//import com.boseyo.backend.dto.FileDto
//import com.boseyo.backend.entity.FileEntity
//import com.boseyo.backend.repository.FileRepository
//import org.springframework.stereotype.Service
//import org.springframework.transaction.annotation.Transactional
//
//@Service
//class FileService(var fileRepository: FileRepository){
//    @Transactional
//    fun saveFile(fileDto: FileDto):Long{
//        return fileRepository.save(fileDto.toEntity()).getId()
//    }
//
//    @Transactional
//    fun getFile(id:Long):FileDto{
//        var file:FileEntity = fileRepository.findById(id).get()
//
//        var fileDto:FileDto = FileDto(id, file.ogFilename, file.fileName, file.filePath)
//
//        return fileDto
//    }
//}