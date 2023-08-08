package com.sparta.hotsixproject.checklist.controller;

import com.sparta.hotsixproject.checklist.checklistItem.dto.ChecklistItemRequestDto;
import com.sparta.hotsixproject.checklist.checklistItem.dto.ChecklistItemResponseDto;
import com.sparta.hotsixproject.checklist.dto.ChecklistRequestDto;
import com.sparta.hotsixproject.checklist.dto.ChecklistResponseDto;
import com.sparta.hotsixproject.checklist.dto.ChecklistsResponseDto;
import com.sparta.hotsixproject.checklist.service.ChecklistService;
import com.sparta.hotsixproject.common.advice.ApiResponseDto;
import com.sparta.hotsixproject.common.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
@Tag(name = "체크리스트 관련 API", description = "카드 체크리스트 관련 API 입니다.")
public class ChecklistController {
    private final ChecklistService checklistService;

    // 체크리스트 만들기
    @PostMapping("/{boardId}/sides/{sideId}/cards/{cardId}/checklists")
    @Operation(summary = "체크리스트 만들기", description = "Dto로 정보를 받아와 해당 카드에 체크리스트를 생성합니다.")
    public ResponseEntity<ApiResponseDto> createChecklist(
            @PathVariable Long boardId, @PathVariable Long sideId, @PathVariable Long cardId,
            @RequestBody ChecklistRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ChecklistResponseDto responseDto = checklistService.createChecklist(boardId, sideId, cardId, requestDto, userDetails.getUser());
        return ResponseEntity.ok().body(new ApiResponseDto("체크리스트가 생성되었습니다.", HttpStatus.CREATED.value()));
    }

    // 체크리스트 전체 조회
    @GetMapping("/{boardId}/sides/{sideId}/cards/{cardId}/checklists")
    @Operation(summary = "체크리스트 전체 조회", description = "해당 카드에 대한 모든 체크리스트 및 아이템을 조회합니다.")
    public ResponseEntity<ApiResponseDto> getChecklists(
            @PathVariable Long boardId, @PathVariable Long sideId, @PathVariable Long cardId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ChecklistsResponseDto responseDto = checklistService.getChecklists(boardId, sideId, cardId, userDetails.getUser());
        return ResponseEntity.ok().body(responseDto);
    }

    // 체크리스트 이름 수정
    @PutMapping("/{boardId}/sides/{sideId}/cards/{cardId}/checklists/{checklistId}")
    @Operation(summary = "체크리스트 이름 수정", description = "Dto로 정보를 받아와 해당 체크리스트 이름을 수정합니다.")
    public ResponseEntity<ApiResponseDto> updateChecklistName(
            @PathVariable Long boardId, @PathVariable Long sideId, @PathVariable Long cardId, @PathVariable Long checklistId,
            @RequestBody ChecklistRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ChecklistResponseDto responseDto = checklistService.updateChecklistName(boardId, sideId, cardId, checklistId, requestDto, userDetails.getUser());
        return ResponseEntity.ok().body(responseDto);
    }

    // 체크리스트 삭제
    @DeleteMapping("/{boardId}/sides/{sideId}/cards/{cardId}/checklists/{checklistId}")
    @Operation(summary = "체크리스트 삭제", description = "해당 체크리스트를 삭제합니다.")
    public ResponseEntity<ApiResponseDto> deleteChecklist(
            @PathVariable Long boardId, @PathVariable Long sideId, @PathVariable Long cardId, @PathVariable Long checklistId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        checklistService.deleteChecklist(boardId, sideId, cardId, checklistId, userDetails.getUser());
        return ResponseEntity.ok().body(new ApiResponseDto("체크리스트가 삭제되었습니다.", HttpStatus.OK.value()));
    }

    // 체크리스트 아이템 추가
    @PostMapping("/{boardId}/sides/{sideId}/cards/{cardId}/checklists/{checklistId}")
    @Operation(summary = "체크리스트 아이템 추가", description = "Dto로 정보를 받아와 해당 체크리스트에 아이템을 추가합니다.")
    public ResponseEntity<ApiResponseDto> createItem(
            @PathVariable Long boardId, @PathVariable Long sideId, @PathVariable Long cardId, @PathVariable Long checklistId,
            @RequestBody ChecklistItemRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ChecklistItemResponseDto responseDto = checklistService.createItem(boardId, sideId, cardId, checklistId, requestDto, userDetails.getUser());
        return ResponseEntity.ok().body(new ApiResponseDto("체크리스트 아이템이 추가되었습니다.", HttpStatus.CREATED.value()));
    }

    // 체크리스트 아이템 체크
    @PutMapping("/{boardId}/sides/{sideId}/cards/{cardId}/checklists/{checklistId}/item/{itemId}/check")
    @Operation(summary = "체크리스트 아이템 체크", description = "해당 체크리스트 아이템의 체크 상태를 변경합니다.")
    public ResponseEntity<ApiResponseDto> updateItemChecked(
            @PathVariable Long boardId, @PathVariable Long sideId, @PathVariable Long cardId, @PathVariable Long checklistId,
            @PathVariable Long itemId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ChecklistItemResponseDto responseDto = checklistService.updateItemChecked(boardId, sideId, cardId, checklistId, itemId, userDetails.getUser());
        return ResponseEntity.ok().body(responseDto);
    }

    // 체크리스트 아이템 수정
    @PutMapping("/{boardId}/sides/{sideId}/cards/{cardId}/checklists/{checklistId}/item/{itemId}/content")
    @Operation(summary = "체크리스트 아이템 내용 변경", description = "해당 체크리스트 아이템의 내용을 변경합니다.")
    public ResponseEntity<ApiResponseDto> updateItemContent(
            @PathVariable Long boardId, @PathVariable Long sideId, @PathVariable Long cardId, @PathVariable Long checklistId,
            @PathVariable Long itemId, @RequestBody ChecklistItemRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ChecklistItemResponseDto responseDto = checklistService.updateItemContent(boardId, sideId, cardId, checklistId, itemId, requestDto, userDetails.getUser());
        return ResponseEntity.ok().body(responseDto);
    }

    // 체크리스트 아이템 삭제
    @DeleteMapping("/{boardId}/sides/{sideId}/cards/{cardId}/checklists/{checklistId}/item/{itemId}")
    @Operation(summary = "체크리스트 아이템 삭제", description = "해당 체크리스트 아이템을 삭제합니다.")
    public ResponseEntity<ApiResponseDto> deleteItem(
            @PathVariable Long boardId, @PathVariable Long sideId, @PathVariable Long cardId, @PathVariable Long checklistId,
            @PathVariable Long itemId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        checklistService.deleteItem(boardId, sideId, cardId, checklistId, itemId, userDetails.getUser());
        return ResponseEntity.ok().body(new ApiResponseDto("체크리스트 아이템이 삭제되었습니다.", HttpStatus.OK.value()));
    }
}
