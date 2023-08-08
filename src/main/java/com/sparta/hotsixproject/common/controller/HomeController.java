package com.sparta.hotsixproject.board.common.controller;

import com.sparta.hotsixproject.board.dto.BoardResponseDto;
import com.sparta.hotsixproject.board.service.BoardService;
import com.sparta.hotsixproject.common.security.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class HomeController {
    private BoardService boardService;
    @GetMapping("/")
    public String goHome(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model ){
        //내가 만든 보드, 내가 참여한 보드 불러오기
        List<BoardResponseDto> getMyBoards = boardService.getMyBoards(userDetails.getUser());
        List<BoardResponseDto> getGuestBoards = boardService.getGuestBoards(userDetails.getUser());
//        return ResponseEntity.ok().body(boardService.getAllBoards());

        model.addAttribute("MyBoards",getMyBoards);
        model.addAttribute("GuestBoards",getGuestBoards);

        return "main";
    }
}
