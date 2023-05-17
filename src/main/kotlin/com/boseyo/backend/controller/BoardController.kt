//package com.boseyo.backend.controller
//
//import com.boseyo.backend.entity.Board
//import com.boseyo.backend.repository.BoardRepository
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.stereotype.Controller
//import org.springframework.web.bind.annotation.*
//
//@RestController
//@RequestMapping("/boards")
//class BoardController @Autowired constructor(
//    private val boardRepository: BoardRepository
//) {
//    @PostMapping("/write")
//    fun creatBoard(@RequestBody board: Board):Board{
//        return boardRepository.save(board)
//    }
//
//    @GetMapping
//    fun readBoard():List<Board>{
//        return boardRepository.findAll();
//    }
//
//    @DeleteMapping("/{id}")
//    fun deleteBoard(@PathVariable id: Int) {
//        if (boardRepository.existsById(id)) {
//            boardRepository.deleteById(id)
//        } else {
//            throw RuntimeException("Board not found")
//        }
//    }
//
//}