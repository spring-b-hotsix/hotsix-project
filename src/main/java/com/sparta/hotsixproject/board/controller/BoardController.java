package com.sparta.hotsixproject.board.controller;

import com.sparta.hotsixproject.board.dto.BoardRequestDto;
import com.sparta.hotsixproject.board.dto.BoardResponseDto;
import com.sparta.hotsixproject.board.dto.InviteBoardRequestDto;
import com.sparta.hotsixproject.board.dto.MemberResponseDto;
import com.sparta.hotsixproject.board.service.BoardService;
import com.sparta.hotsixproject.card.dto.CardResponseDto;
import com.sparta.hotsixproject.common.advice.ApiResponseDto;
import com.sparta.hotsixproject.common.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@Tag(name = "보드 관련 API", description = "보드 관련 API 입니다.")
public class BoardController {

    private BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @ResponseBody
    @GetMapping("/boards")
    @Operation(summary = "보드 전체 조회", description = "내가 생성했거나, 게스트로 추가된 모든 보드를 조회합니다.")
    public ResponseEntity<List<BoardResponseDto>> getBoards(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<BoardResponseDto> boards = new ArrayList<>();
        boards.addAll(boardService.getMyBoards(userDetails.getUser()));
        boards.addAll(boardService.getGuestBoards(userDetails.getUser()));
        return ResponseEntity.status(HttpStatus.OK).body(boards);
    }
    @ResponseBody
    @GetMapping("/myboard")
    @Operation(summary = "내 보드 전체 조회", description = "내가 생성한 모든 보드를 조회합니다.")
    public ResponseEntity<List<BoardResponseDto>> getMyBoards(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(boardService.getMyBoards(userDetails.getUser()));
    }

    @GetMapping("/guestboard")
    @Operation(summary = "내가 초대된 보드 전체 조회", description = "내가 게스트로 추가된 모든 보드를 조회합니다.")
    public ResponseEntity<List<BoardResponseDto>> getGuestBoards(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(boardService.getGuestBoards(userDetails.getUser()));
    }

    @GetMapping("/boards/{boardId}")
    @Operation(summary = "보드 1개 조회", description = "선택한 보드를 조회합니다.")
    public String getBoard(
            @Parameter(name = "boardId", description = "조회할 board의 id", in = ParameterIn.PATH) @PathVariable Long boardId,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            Model model
    ) {
        BoardResponseDto board = boardService.getBoard(boardId, userDetails.getUser());
        model.addAttribute("board",board);
        return "board";
    }
    @ResponseBody
    @GetMapping("/boards/{boardId}/word")
    @Operation(summary = "보드에서 카드리스트 검색", description = "입력한 키워드로 카드 리스트를 조회합니다.")
    public List<CardResponseDto> searchCards(
            @Parameter(name = "boardId", description = "조회할 board의 id", in = ParameterIn.PATH) @PathVariable Long boardId,
            @Parameter(name = "keyword", description = "검색할 keyword", in = ParameterIn.QUERY) String keyword,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.searchCards(boardId, keyword, userDetails.getUser());
    }

    @ResponseBody
    @PostMapping("/boards")
    @Operation(summary = "보드 생성", description = "보드를 생성하고, 유저 정보에 생성된 보드를 추가합니다.")
    public ResponseEntity<ApiResponseDto> createBoard(
            @Parameter(description = "보드 생성 및 수정 시 필요한 정보") @RequestBody BoardRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(boardService.createBoard(requestDto, userDetails.getUser()));
    }
    @ResponseBody
    @PutMapping("/boards/{boardId}")
    @Operation(summary = "보드 수정", description = "선택한 보드를 수정합니다.")
    public ResponseEntity<ApiResponseDto> updateBoard(
            @Parameter(name = "boardId", description = "수정할 board의 id", in = ParameterIn.PATH) @PathVariable Long boardId,
            @RequestBody BoardRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(boardService.updateBoard(boardId, requestDto, userDetails.getUser()));
    }
    @ResponseBody
    @DeleteMapping("/boards/{boardId}")
    @Operation(summary = "보드 삭제", description = "선택한 보드를 삭제합니다.")
    public ResponseEntity<ApiResponseDto> deleteBoard(
            @Parameter(name = "boardId", description = "삭제할 board의 id", in = ParameterIn.PATH) @PathVariable Long boardId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(boardService.deleteBoard(boardId, userDetails.getUser()));
    }
    @ResponseBody
    @PostMapping("/boards/{boardId}/members")
    @Operation(summary = "보드에 게스트 초대(추가)", description = "선택한 보드에 사용자를 초대(추가)합니다.")
    public ResponseEntity<ApiResponseDto> inviteBoard(
            @Parameter(name = "boardId", description = "게스트를 초대할 board의 id", in = ParameterIn.PATH) @PathVariable Long boardId,
            @Parameter(description = "초대할 사용자의 정보 (email)") @RequestBody InviteBoardRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(boardService.inviteBoard(boardId, requestDto.getEmail(), userDetails.getUser()));
    }
    @ResponseBody
    // 보드 멤버 전체 조회
    @GetMapping("/boards/{boardId}/members")
    @Operation(summary = "해당 보드의 게스트 전체 조회", description = "선택한 보드에 추가된 모든 사용자를 조회합니다.")
    public List<MemberResponseDto> getMembers(
            @Parameter(name = "boardId", description = "사용자를 조회할 board의 id", in = ParameterIn.PATH) @PathVariable Long boardId
    ) {
        return boardService.getMembers(boardId);
    }

    @ResponseBody
    // 보드 멤버 삭제
    @DeleteMapping ("/boards/{boardId}/members/{memberId}")
    @Operation(summary = "해당 보드의 참여자 삭제", description = "선택한 보드에서 선택된 사용자를 참여자에서 제외합니다.")
    public ApiResponseDto deleteMember(
            @Parameter(name = "boardId", description = "board의 id", in = ParameterIn.PATH) @PathVariable Long boardId,
            @Parameter(name = "memberId", description = "삭제할 사용자의 id", in = ParameterIn.PATH) @PathVariable Long memberId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return boardService.deleteMember(boardId,memberId,userDetails.getUser());
    }
}
