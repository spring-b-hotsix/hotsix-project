package com.sparta.hotsixproject.card.controller;

import com.sparta.hotsixproject.card.dto.*;
import com.sparta.hotsixproject.card.service.CardService;
import com.sparta.hotsixproject.common.advice.ApiResponseDto;
import com.sparta.hotsixproject.common.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class CardController {

    public final CardService cardService;

    // 카드 생성
    @PostMapping("/{boardId}/sides/{sideId}/cards")
    public ResponseEntity<ApiResponseDto> createCard(@PathVariable Long boardId, @PathVariable Long sideId,
                                                     @RequestBody CardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return cardService.createCard(sideId, requestDto.getName(), userDetails.getUser());
    }

    // 카드 상세 조회
    @GetMapping("/{boardId}/sides/{sideId}/cards/{cardId}")
    public ResponseEntity<CardResponseDto> getCard(@PathVariable Long boardId, @PathVariable Long sideId, @PathVariable Long cardId) {
        return cardService.getCard(boardId, sideId, cardId);
    }

    // side 안의 카드 전체 조회
    @GetMapping("/{boardId}/sides/{sideId}/cards")
    public List<CardResponseDto> getCards(@PathVariable Long boardId, @PathVariable Long sideId) {
        return cardService.getCards(boardId, sideId);
    }

    // 카드 작업자 추가
    @PostMapping("/{boardId}/sides/{sideId}/cards/{cardId}/worker")
    public ResponseEntity<CardResponseDto> addWorker(@PathVariable Long boardId, @PathVariable Long sideId, @PathVariable Long cardId, @RequestBody InviteCardRequestDto requestDto) {
        return cardService.addWorker(boardId, sideId, cardId, requestDto.getEmail());
    }

    // 카드 이름 수정
    @PutMapping("/{boardId}/sides/{sideId}/cards/{cardId}/name")
    public ResponseEntity<CardResponseDto> updateName(@PathVariable Long boardId, @PathVariable Long sideId, @PathVariable Long cardId,
                                                      @RequestBody NameRequestDto requestDto) {
        return cardService.updateName(boardId, sideId, cardId, requestDto.getName());
    }

    // 카드 설명 수정
    @PutMapping("/{boardId}/sides/{sideId}/cards/{cardId}/desc")
    public ResponseEntity<CardResponseDto> updateDesc(@PathVariable Long boardId, @PathVariable Long sideId, @PathVariable Long cardId,
                                                      @RequestBody DescRequestDto requestDto) {
        return cardService.updateDesc(boardId, sideId, cardId, requestDto.getDescription());
    }

    // 카드 색상 수정
    @PutMapping("/{boardId}/sides/{sideId}/cards/{cardId}/color")
    public ResponseEntity<CardResponseDto> updateColor(@PathVariable Long boardId, @PathVariable Long sideId, @PathVariable Long cardId,
                                                       @RequestBody ColorRequestDto requestDto) {
        return cardService.updateColor(boardId, sideId, cardId, requestDto.getColor());
    }

    // 카드 마감 기한 설정 및 수정
    @PutMapping("/{boardId}/sides/{sideId}/cards/{cardId}/due")
    public ResponseEntity<CardResponseDto> updateDue(@PathVariable Long boardId, @PathVariable Long sideId, @PathVariable Long cardId,
                                                      @RequestBody DueRequestDto requestDto) {
        return cardService.updateDue(boardId, sideId, cardId, requestDto);
    }

    // 카드 마감 기한 삭제
    @PutMapping("/{boardId}/sides/{sideId}/cards/{cardId}/due-removal")
    public ResponseEntity<CardResponseDto> updateDue(@PathVariable Long boardId, @PathVariable Long sideId, @PathVariable Long cardId) {
        return cardService.updateDueRemoval(boardId, sideId, cardId);
    }

    // 카드 이동
    @PutMapping("/{boardId}/sides/{sideId}/cards/{cardId}/position")
    public ResponseEntity<CardResponseDto> moveCard(@PathVariable Long boardId, @PathVariable Long sideId, @PathVariable Long cardId,
                                                    @RequestBody MoveRequestDto requestDto ) {
        return cardService.moveCard(boardId, sideId, cardId, requestDto);
    }
    
    // 카드 삭제
    @DeleteMapping("/{boardId}/sides/{sideId}/cards/{cardId}")
    public ResponseEntity<ApiResponseDto> deleteCard(@PathVariable Long boardId, @PathVariable Long sideId, @PathVariable Long cardId,
                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return cardService.deleteCard(boardId, sideId, cardId, userDetails.getUser());
    }
}
