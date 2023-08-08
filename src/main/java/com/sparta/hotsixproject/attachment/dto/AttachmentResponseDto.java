package com.sparta.hotsixproject.attachment.dto;

import com.sparta.hotsixproject.attachment.entity.Attachment;
import lombok.Getter;

@Getter
public class AttachmentResponseDto {
    private Long id;
    private String fileName;
    private String source;

    public AttachmentResponseDto(Attachment attachment) {
        this.id = attachment.getId();
        this.fileName = attachment.getFileName();
        this.source = attachment.getSource();
    }
}
