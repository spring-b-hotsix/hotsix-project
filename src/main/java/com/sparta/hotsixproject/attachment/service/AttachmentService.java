package com.sparta.hotsixproject.attachment.service;

import com.sparta.hotsixproject.attachment.dto.AttachmentResponseDto;
import com.sparta.hotsixproject.attachment.entity.Attachment;
import com.sparta.hotsixproject.attachment.repository.AttachmentRepository;
import com.sparta.hotsixproject.card.entity.Card;
import com.sparta.hotsixproject.card.repository.CardRepository;
import com.sparta.hotsixproject.common.advice.ApiResponseDto;
import com.sparta.hotsixproject.common.file.FileUploader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final FileUploader fileUploader;
    private final CardRepository cardRepository;

    // 파일 첨부
    @Transactional
    public ResponseEntity<ApiResponseDto> createFile(Long boardId, Long sideId, Long cardId, MultipartFile file)
            throws IOException {
        Card card = cardRepository.findBySide_Board_IdAndSide_IdAndId(boardId, sideId, cardId);
        if (file != null) {
            String source = fileUploader.upload(file, "file");
            Attachment attachment = new Attachment(source, card);
            attachmentRepository.save(attachment);
        }

        ApiResponseDto apiResponseDto = new ApiResponseDto("파일 추가 완료", HttpStatus.CREATED.value());
        return new ResponseEntity<>(apiResponseDto, HttpStatus.CREATED);
    }
    
    // 카드의 파일 전체 조회
    @Transactional(readOnly = true)
    public List<AttachmentResponseDto> getFiles(Long boardId, Long sideId, Long cardId) {
        return attachmentRepository.findByCard_Id(cardId).stream().map(AttachmentResponseDto::new).toList();
    }
    
    // 파일 이름 수정
    @Transactional
    public ResponseEntity<AttachmentResponseDto> updateFile(Long boardId, Long sideId, Long cardId, Long fileId, String fileName) {
        Attachment attachment = attachmentRepository.findById(fileId).get();
        attachment.updateName(fileName);

        AttachmentResponseDto attachmentResponseDto = new AttachmentResponseDto(attachment);
        return new ResponseEntity<>(attachmentResponseDto, HttpStatus.OK);
    }
    
    // 파일 삭제
    @Transactional
    public ResponseEntity<ApiResponseDto> deleteFile(Long boardId, Long sideId, Long cardId, Long fileId) {
        Attachment attachment = attachmentRepository.findById(fileId).get();
        attachmentRepository.delete(attachment);

        ApiResponseDto apiResponseDto = new ApiResponseDto("파일 삭제 완료", HttpStatus.OK.value());
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }
}
