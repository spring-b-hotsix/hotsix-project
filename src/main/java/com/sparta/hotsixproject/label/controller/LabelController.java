package com.sparta.hotsixproject.label.controller;

import com.sparta.hotsixproject.common.advice.ApiResponseDto;
import com.sparta.hotsixproject.label.dto.LabelRequestDto;
import com.sparta.hotsixproject.label.dto.LabelResponseDto;
import com.sparta.hotsixproject.label.entity.Label;
import com.sparta.hotsixproject.label.service.LabelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class LabelController {

    public final LabelService labelService;

    // 라벨 생성
    @PostMapping("/{boardId}/sides/{sideId}/cards/{cardId}/labels")
    public ResponseEntity<ApiResponseDto> createLabel(@PathVariable Long boardId, @PathVariable Long sideId,
                                                      @PathVariable Long cardId, @RequestBody LabelRequestDto requestDto) {
        return labelService.createLabel(boardId, sideId, cardId, requestDto);
    }

    // 라벨 전체 조회
    @GetMapping("/{boardId}/sides/{sideId}/cards/{cardId}/labels")
    public List<LabelResponseDto> getLabels(@PathVariable Long boardId, @PathVariable Long sideId, @PathVariable Long cardId) {
        return labelService.getLabels(boardId, sideId, cardId);
    }
}
