package com.boseyo.backend.service

import com.boseyo.backend.dto.BoardDto
import com.boseyo.backend.dto.MakeDto
import com.boseyo.backend.entity.BoardEntity
import com.boseyo.backend.repository.BoardRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

@Service
class BoardService (val boardRepository: BoardRepository){
    private fun encodeImageToFile(imageBase64: String?): String {
        val encodedImageBytes = Base64.getDecoder().decode(imageBase64)
        val fileName = "image_${System.currentTimeMillis()}.png"
        val filePath = Paths.get("image_directory", fileName) // 이미지 파일이 저장될 경로

        Files.write(filePath, encodedImageBytes)

        return filePath.toString()
    }

    @Transactional
    fun savePost(boardDto: BoardDto, makeDto: MakeDto): Long? {
        val imageFilePath = makeDto.imageBase64?.let { encodeImageToFile(it) }
        val makeDtoWithImageFile = makeDto.copy(imageFile = imageFilePath)
        val boardDtoWithImageFilePath = boardDto.copy(makeDto = makeDtoWithImageFile)

        return boardRepository.save(boardDtoWithImageFilePath.toEntity()).id
    }

    @Transactional
    fun getBoardList(): List<BoardDto> {
        val boardList: List<BoardEntity> = boardRepository.findAll()
        val boardDtoList: MutableList<BoardDto> = mutableListOf()

        for (board in boardList) {
            val imageFile = board.imageFile
            val boardDto = BoardDto(
                board.title,
                board.fontName,
                board.fontGenerator,
                board.fontCreatedDate,
                MakeDto(imageFile = imageFile) //이미지 파일 경로 추가된 makeDto 추가
            )
            boardDtoList.add(boardDto)
        }
        return boardDtoList
    }

    @Transactional
    fun getPost(id: Long): BoardDto {
        val board: BoardEntity = boardRepository.findById(id.toInt()).get()
        val imageFile = board.imageFile

        val boardDto: BoardDto = BoardDto(
            board.title,
            board.fontName,
            board.fontGenerator,
            board.fontCreatedDate,
            MakeDto(imageFile = imageFile) //이미지 파일 경로 추가된 makeDto 추가
        )
        return boardDto
    }

    @Transactional
    fun deletePost(id: Long) {
        boardRepository.deleteById(id.toInt())
    }
}