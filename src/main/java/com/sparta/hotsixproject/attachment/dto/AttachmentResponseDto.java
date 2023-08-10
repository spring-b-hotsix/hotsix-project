package com.sparta.hotsixproject.attachment.dto;

import com.sparta.hotsixproject.attachment.entity.Attachment;
import lombok.Getter;

@Getter
public class AttachmentResponseDto {
    private Long fileId;
    private String fileName;
    private String source;

    public AttachmentResponseDto(Attachment attachment) {
        this.fileId = attachment.getId();
        this.fileName = attachment.getFileName();
        this.source = attachment.getSource();
    }
}
