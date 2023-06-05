package com.boseyo.backend.controller

import com.boseyo.backend.dto.BoardDto
import com.boseyo.backend.entity.BoardEntity
import com.boseyo.backend.service.BoardService
import jakarta.persistence.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/board")
class BoardController (@Autowired private val boardService: BoardService){
    //게시글 작성
    @PostMapping("/write")
    fun writePost(boardDto: BoardDto):Long?{
        return boardService.savePost(boardDto)
    }

    //게시판 작성 글 목록
    @GetMapping
    fun getPostList():List<BoardDto>{
        return boardService.getBoardList()
    }

    //게시글 불러오기
    @GetMapping("/{postId}")
    fun getPostContent(@PathVariable postId:Long):BoardDto{
        return boardService.getPost(postId)
    }

    //게시글 삭제
    @DeleteMapping("/{postId}")
    @ResponseBody
    fun deletePost(@PathVariable postId:Long):Boolean{
        return Result.runCatching { boardService.deletePost(postId) }.isSuccess
    }

}