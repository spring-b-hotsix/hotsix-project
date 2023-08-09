package com.sparta.hotsixproject.card.dto;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class DueRequestDto {

    @Nullable
    private LocalDateTime due;
}
