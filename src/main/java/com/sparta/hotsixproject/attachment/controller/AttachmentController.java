package com.sparta.hotsixproject.attachment.controller;

import com.sparta.hotsixproject.attachment.dto.AttachmentNameRequestDto;
import com.sparta.hotsixproject.attachment.dto.AttachmentResponseDto;
import com.sparta.hotsixproject.attachment.service.AttachmentService;
import com.sparta.hotsixproject.common.advice.ApiResponseDto;
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
public class AttachmentController {
    public final AttachmentService attachmentService;
    
    // 파일 첨부
    @PostMapping("/{boardId}/sides/{sideId}/cards/{cardId}/files")
    public ResponseEntity<ApiResponseDto> createFile(@PathVariable Long boardId, @PathVariable Long sideId, @PathVariable Long cardId,
                                                       @RequestParam MultipartFile file) throws IOException {
        return attachmentService.createFile(boardId, sideId, cardId, file);
    }
    
    // 카드의 파일 전체 조회
    @GetMapping("/{boardId}/sides/{sideId}/cards/{cardId}/files")
    public List<AttachmentResponseDto> getAttachments(@PathVariable Long boardId, @PathVariable Long sideId, @PathVariable Long cardId) {
        return attachmentService.getFiles(boardId, sideId, cardId);
    }

    // 파일 이름 수정
    @PutMapping("/{boardId}/sides/{sideId}/cards/{cardId}/files/{fileId}")
    public ResponseEntity<AttachmentResponseDto> updateFile(@PathVariable Long boardId, @PathVariable Long sideId, @PathVariable Long cardId,
                                                            @PathVariable Long fileId, @RequestBody AttachmentNameRequestDto requestDto) {
        return attachmentService.updateFile(boardId, sideId, cardId, fileId, requestDto.getFileName());
    }

    // 파일 삭제
    @DeleteMapping("/{boardId}/sides/{sideId}/cards/{cardId}/files/{fileId}")
    public ResponseEntity<ApiResponseDto> deleteFile(@PathVariable Long boardId, @PathVariable Long sideId, @PathVariable Long cardId,
                                                     @PathVariable Long fileId) {
        return attachmentService.deleteFile(boardId, sideId, cardId, fileId);
    }
}
