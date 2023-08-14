package com.sparta.hotsixproject.attachment.dto;

import com.sparta.hotsixproject.attachment.entity.Attachment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AttachmentResponseDto {
    private Long fileId;
    private String fileName;
    private String source;
    private LocalDateTime createdAt;

    public AttachmentResponseDto(Attachment attachment) {
        this.fileId = attachment.getId();
        this.fileName = attachment.getFileName();
        this.source = attachment.getSource();
        this.createdAt = attachment.getCreatedAt();
    }
}
