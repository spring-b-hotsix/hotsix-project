package com.sparta.hotsixproject.label.controller;

import com.sparta.hotsixproject.common.advice.ApiResponseDto;
import com.sparta.hotsixproject.common.security.UserDetailsImpl;
import com.sparta.hotsixproject.label.dto.LabelRequestDto;
import com.sparta.hotsixproject.label.dto.LabelResponseDto;
import com.sparta.hotsixproject.label.service.LabelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
@Tag(name = "라벨 관련 API", description = "라벨 관련 API 입니다.")
public class LabelController {
    public final LabelService labelService;

    // 라벨 생성
    @PostMapping("/{boardId}/sides/{sideId}/cards/{cardId}/labels")
    @Operation(summary = "라벨 생성(추가)", description = "새로운 라벨을 추가합니다.")
    public ResponseEntity<ApiResponseDto> createLabel(
            @Parameter(name = "boardId", description = "선택한 카드가 위치한 board의 id", in = ParameterIn.PATH) @PathVariable Long boardId,
            @Parameter(name = "sideId", description = "선택한 카드가 위치한 side의 id", in = ParameterIn.PATH) @PathVariable Long sideId,
            @Parameter(name = "cardId", description = "라벨을 추가할 card의 id", in = ParameterIn.PATH) @PathVariable Long cardId,
            @Parameter(description = "라벨 생성 시 필요한 정보 (title, color)") @RequestBody LabelRequestDto requestDto
    ) {
        return labelService.createLabel(boardId, sideId, cardId, requestDto);
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
    @Operation(summary = "카드에 추가된 라벨 전체 조회", description = "카드에 추가된 모든 라벨을 조회합니다.")
    public List<LabelResponseDto> getLabels(
            @Parameter(name = "boardId", description = "선택한 카드가 위치한 board의 id", in = ParameterIn.PATH) @PathVariable Long boardId,
            @Parameter(name = "sideId", description = "선택한 카드가 위치한 side의 id", in = ParameterIn.PATH) @PathVariable Long sideId,
            @Parameter(name = "cardId", description = "라벨 정보를 가져올 card의 id", in = ParameterIn.PATH) @PathVariable Long cardId
    ) {
        return labelService.getLabels(boardId, sideId, cardId);
    }
}
