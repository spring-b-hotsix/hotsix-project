package com.sparta.hotsixproject.attachment.controller;

import com.sparta.hotsixproject.attachment.dto.AttachmentNameRequestDto;
import com.sparta.hotsixproject.attachment.dto.AttachmentResponseDto;
import com.sparta.hotsixproject.attachment.service.AttachmentService;
import com.sparta.hotsixproject.common.advice.ApiResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
@Tag(name = "첨부 파일 관련 API", description = "첨부 파일 관련 API 입니다.")
public class AttachmentController {
    public final AttachmentService attachmentService;

    @PostMapping("/{boardId}/sides/{sideId}/cards/{cardId}/files")
    @Operation(summary = "첨부 파일 생성(추가)", description = "해당 위치에 첨부 파일을 추가합니다.")
    public ResponseEntity<ApiResponseDto> createFile(
            @Parameter(name = "boardId", description = "선택한 카드가 위치한 board의 id", in = ParameterIn.PATH) @PathVariable Long boardId,
            @Parameter(name = "sideId", description = "선택한 카드가 위치한 side의 id", in = ParameterIn.PATH) @PathVariable Long sideId,
            @Parameter(name = "cardId", description = "파일을 첨부할 card의 id", in = ParameterIn.PATH) @PathVariable Long cardId,
            @Parameter(name = "file", description = "첨부할 파일", in = ParameterIn.PATH) @RequestParam MultipartFile file
    ) throws IOException {
        return attachmentService.createFile(boardId, sideId, cardId, file);
    }
    
    // 카드의 파일 전체 조회
    @GetMapping("/{boardId}/sides/{sideId}/cards/{cardId}/files")
    @Operation(summary = "첨부 파일 전체 조회", description = "해당 위치에 있는 모든 첨부 파일을 조회합니다.")
    public List<AttachmentResponseDto> getAttachments(
            @Parameter(name = "boardId", description = "선택한 카드가 위치한 board의 id", in = ParameterIn.PATH) @PathVariable Long boardId,
            @Parameter(name = "sideId", description = "선택한 카드가 위치한 side의 id", in = ParameterIn.PATH) @PathVariable Long sideId,
            @Parameter(name = "cardId", description = "첨부 파일을 조회할 card의 id", in = ParameterIn.PATH) @PathVariable Long cardId
    ) {
        return attachmentService.getFiles(boardId, sideId, cardId);
    }

    // 파일 이름 수정
    @PutMapping("/{boardId}/sides/{sideId}/cards/{cardId}/files/{fileId}")
    @Operation(summary = "첨부 파일 이름 수정", description = "선택한 첨부 파일의 이름을 수정합니다.")
    public ResponseEntity<AttachmentResponseDto> updateFile(
            @Parameter(name = "boardId", description = "선택한 카드가 위치한 board의 id", in = ParameterIn.PATH) @PathVariable Long boardId,
            @Parameter(name = "sideId", description = "선택한 카드가 위치한 side의 id", in = ParameterIn.PATH) @PathVariable Long sideId,
            @Parameter(name = "cardId", description = "파일이 첨부된 card의 id", in = ParameterIn.PATH) @PathVariable Long cardId,
            @Parameter(name = "fileId", description = "이름을 수정할 file의 id", in = ParameterIn.PATH) @PathVariable Long fileId,
            @Parameter(description = "첨부 파일 이름 수정 정보") @RequestBody AttachmentNameRequestDto requestDto
    ) {
        return attachmentService.updateFile(boardId, sideId, cardId, fileId, requestDto.getFileName());
    }

    // 파일 삭제
    @DeleteMapping("/{boardId}/sides/{sideId}/cards/{cardId}/files/{fileId}")
    @Operation(summary = "첨부 파일 삭제", description = "선택한 첨부 파일을 삭제합니다.")
    public ResponseEntity<ApiResponseDto> deleteFile(
            @Parameter(name = "boardId", description = "선택한 카드가 위치한 board의 id", in = ParameterIn.PATH) @PathVariable Long boardId,
            @Parameter(name = "sideId", description = "선택한 카드가 위치한 side의 id", in = ParameterIn.PATH) @PathVariable Long sideId,
            @Parameter(name = "cardId", description = "파일이 첨부된 card의 id", in = ParameterIn.PATH) @PathVariable Long cardId,
            @Parameter(name = "fileId", description = "삭제할 file의 id", in = ParameterIn.PATH) @PathVariable Long fileId
    ) {
        return attachmentService.deleteFile(boardId, sideId, cardId, fileId);
    }
}
