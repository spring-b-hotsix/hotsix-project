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
    @PostMapping("/{boardId}/labels")
    @Operation(summary = "보드 라벨 생성", description = "보드에 새로운 라벨을 생성합니다.")
    public ResponseEntity<ApiResponseDto> createLabel(@Parameter(name = "boardId", description = "라벨을 만들 board의 id", in = ParameterIn.PATH) @PathVariable Long boardId,
                                                      @Parameter(description = "라벨 생성 시 필요한 정보 (title, color)") @RequestBody LabelRequestDto requestDto,
                                                      @Parameter(description = "로그인 한 User의 정보") @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return labelService.createLabel(boardId, requestDto, userDetails.getUser());
    }

    // 해당 보드에 대한 라벨 전체 조회
    @Operation(summary = "해당 보드에 대한 라벨 전체 조회", description = "보드에 생성된 모든 라벨을 조회합니다.")
    @GetMapping("/{boardId}/labels")
    public List<LabelResponseDto> getLabels(@Parameter(name = "boardId", description = "라벨을 만들 board의 id", in = ParameterIn.PATH) @PathVariable Long boardId,
                                            @Parameter(description = "로그인 한 User의 정보") @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return labelService.getLabels(boardId, userDetails.getUser());
    }

    // 라벨 수정
    @Operation(summary = "보드 라벨 수정", description = "보드에 생성된 라벨을 수정합니다.")
    @PutMapping("/{boardId}/labels/{labelId}")
    public ResponseEntity<LabelResponseDto> updateLabel(@Parameter(name = "boardId", description = "라벨을 만들 board의 id", in = ParameterIn.PATH) @PathVariable Long boardId,
                                                        @Parameter(name = "labelId", description = "수정할 label의 id", in = ParameterIn.PATH) @PathVariable Long labelId,
                                                        @Parameter(description = "라벨 생성 시 필요한 정보 (title, color)") @RequestBody LabelRequestDto requestDto,
                                                        @Parameter(description = "로그인 한 User의 정보") @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return labelService.updateLabel(boardId, labelId, requestDto, userDetails.getUser());
    }

    // 라벨 삭제
    @Operation(summary = "보드 라벨 삭제", description = "보드에 생성된 라벨을 삭제합니다.")
    @DeleteMapping("/{boardId}/labels/{labelId}")
    public ResponseEntity<ApiResponseDto> deleteLabel(@Parameter(name = "boardId", description = "라벨을 만들 board의 id", in = ParameterIn.PATH) @PathVariable Long boardId,
                                                      @Parameter(name = "labelId", description = "수정할 label의 id", in = ParameterIn.PATH) @PathVariable Long labelId,
                                                      @Parameter(description = "로그인 한 User의 정보") @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return labelService.deleteLabel(boardId, labelId, userDetails.getUser());
    }

    // 카드에 라벨 추가
    @Operation(summary = "카드에 라벨 추가", description = "보드에 생성된 라벨을 카드에 추가합니다.")
    @PostMapping("/{boardId}/sides/{sideId}/cards/{cardId}/labels/{labelId}")
    public ResponseEntity<ApiResponseDto> createCardLabel(@Parameter(name = "boardId", description = "선택한 카드가 위치한 board의 id", in = ParameterIn.PATH) @PathVariable Long boardId,
                                                          @Parameter(name = "sideId", description = "선택한 카드가 위치한 side의 id", in = ParameterIn.PATH) @PathVariable Long sideId,
                                                          @Parameter(name = "cardId", description = "라벨 정보를 가져올 card의 id", in = ParameterIn.PATH) @PathVariable Long cardId,
                                                          @Parameter(name = "labelId", description = "카드에 추가할 label의 id", in = ParameterIn.PATH) @PathVariable Long labelId,
                                                          @Parameter(description = "로그인 한 User의 정보") @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return labelService.createCardLabel(boardId, sideId, cardId, labelId, userDetails.getUser());
    }

    // 카드에 추가된 라벨 전체 조회
    @Operation(summary = "카드에 추가된 라벨 전체 조회", description = "카드에 추가된 모든 라벨을 조회합니다.")
    @GetMapping("/{boardId}/sides/{sideId}/cards/{cardId}/labels")
    public List<LabelResponseDto> getCardLabels(@Parameter(name = "boardId", description = "선택한 카드가 위치한 board의 id", in = ParameterIn.PATH) @PathVariable Long boardId,
                                                @Parameter(name = "sideId", description = "선택한 카드가 위치한 side의 id", in = ParameterIn.PATH) @PathVariable Long sideId,
                                                @Parameter(name = "cardId", description = "라벨 정보를 가져올 card의 id", in = ParameterIn.PATH) @PathVariable Long cardId,
                                                @Parameter(description = "로그인 한 User의 정보") @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return labelService.getCardLabels(boardId, sideId, cardId, userDetails.getUser());
    }

    // 카드에 라벨 삭제
    @Operation(summary = "카드 라벨 삭제", description = "카드에 추가된 라벨을 삭제합니다.")
    @DeleteMapping("/{boardId}/sides/{sideId}/cards/{cardId}/labels/{labelId}")
    public ResponseEntity<ApiResponseDto> deleteCardLabel(@Parameter(name = "boardId", description = "선택한 카드가 위치한 board의 id", in = ParameterIn.PATH) @PathVariable Long boardId,
                                                          @Parameter(name = "sideId", description = "선택한 카드가 위치한 side의 id", in = ParameterIn.PATH) @PathVariable Long sideId,
                                                          @Parameter(name = "cardId", description = "라벨 정보를 가져올 card의 id", in = ParameterIn.PATH) @PathVariable Long cardId,
                                                          @Parameter(name = "labelId", description = "카드에 추가할 label의 id", in = ParameterIn.PATH) @PathVariable Long labelId,
                                                          @Parameter(description = "로그인 한 User의 정보") @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return labelService.deleteCardLabel(boardId, sideId, cardId, labelId, userDetails.getUser());
    }
}
