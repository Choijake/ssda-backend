package com.boseyo.backend.service

import com.boseyo.backend.dto.BoardDto
import com.boseyo.backend.entity.BoardEntity
import com.boseyo.backend.repository.BoardRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardService (val boardRepository: BoardRepository){
    @Transactional
    fun savePost(boardDto: BoardDto):Long?{
        return boardRepository.save(boardDto.toEntity()).id
    }

    @Transactional
    fun getBoardList():List<BoardDto>{
        var boardList:List<BoardEntity> = boardRepository.findAll()
        var boardDtoList: MutableList<BoardDto> = mutableListOf()

        for(board in boardList){
            var boardDto:BoardDto = BoardDto(
                board.id,
                board.fontName,
                board.fontGenerator,
                board.fileId,
                board.fontCreatedDate)
            boardDtoList.add(boardDto)
        }
        return boardDtoList
    }

    @Transactional
    fun getPost(id: Long): BoardDto {
        val board: BoardEntity = boardRepository.findById(id.toInt()).get()

        val boardDto: BoardDto = BoardDto(
            board.id,
            board.fontName,
            board.fontGenerator,
            board.fileId,
            board.fontCreatedDate
        )
        return boardDto
    }

    @Transactional
    fun deletePost(id: Long) {
        boardRepository.deleteById(id.toInt())
    }
}