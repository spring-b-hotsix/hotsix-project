package com.sparta.hotsixproject.label.controller;

import com.sparta.hotsixproject.common.advice.ApiResponseDto;
import com.sparta.hotsixproject.common.security.UserDetailsImpl;
import com.sparta.hotsixproject.label.dto.LabelRequestDto;
import com.sparta.hotsixproject.label.dto.LabelResponseDto;
import com.sparta.hotsixproject.label.service.LabelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class LabelController {

    public final LabelService labelService;

    // 라벨 생성
    @PostMapping("/{boardId}/labels")
    public ResponseEntity<ApiResponseDto> createLabel(@PathVariable Long boardId, @RequestBody LabelRequestDto requestDto,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return labelService.createLabel(boardId, requestDto, userDetails.getUser());
    }

    // 해당 보드에 대한 라벨 전체 조회
    @GetMapping("/{boardId}/labels")
    public List<LabelResponseDto> getLabels(@PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return labelService.getLabels(boardId, userDetails.getUser());
    }

    // 라벨 수정
    @PutMapping("/{boardId}/labels/{labelId}")
    public ResponseEntity<LabelResponseDto> updateLabel(@PathVariable Long boardId, @PathVariable Long labelId,
                                                        @RequestBody LabelRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return labelService.updateLabel(boardId, labelId, requestDto, userDetails.getUser());
    }

    // 라벨 삭제
    @DeleteMapping("/{boardId}/labels/{labelId}")
    public ResponseEntity<ApiResponseDto> deleteLabel(@PathVariable Long boardId, @PathVariable Long labelId,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return labelService.deleteLabel(boardId, labelId, userDetails.getUser());
    }

    // 카드에 라벨 추가
    @PostMapping("/{boardId}/sides/{sideId}/cards/{cardId}/labels/{labelId}")
    public ResponseEntity<ApiResponseDto> createCardLabel(@PathVariable Long boardId, @PathVariable Long sideId, @PathVariable Long cardId, @PathVariable Long labelId,
                                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return labelService.CreateCardLabel(boardId, sideId, cardId, labelId, userDetails.getUser());
    }

    // 카드에 추가된 라벨 전체 조회
    @GetMapping("/{boardId}/sides/{sideId}/cards/{cardId}/labels")
    public List<LabelResponseDto> getCardLabels(@PathVariable Long boardId, @PathVariable Long sideId, @PathVariable Long cardId,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return labelService.getCardLabels(boardId, sideId, cardId,userDetails.getUser());
    }

    // 카드에 라벨 삭제
    @DeleteMapping("/{boardId}/sides/{sideId}/cards/{cardId}/labels/{labelId}")
    public ResponseEntity<ApiResponseDto> deleteCardLabel(@PathVariable Long boardId, @PathVariable Long sideId, @PathVariable Long cardId,
                                                          @PathVariable Long labelId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return labelService.deleteCardLabel(boardId, sideId, cardId, labelId, userDetails.getUser());
    }
}
