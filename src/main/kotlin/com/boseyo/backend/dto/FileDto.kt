//package com.boseyo.backend.dto
//
//import com.boseyo.backend.entity.FileEntity
//import lombok.Getter
//
//@Getter
//data class FileDto(
//    val id:Long,
//    var ogFilename:String,
//    var fileName:String,
//    var filePath:String
//){
//    fun toEntity(): FileEntity {
//        return FileEntity(id, ogFilename, fileName, filePath)
//    }
//}