package com.sparta.hotsixproject.board.dto;

import jakarta.persistence.Column;
import lombok.Getter;

import java.awt.*;

@Getter
public class BoardRequestDto {
    String name;
    String description;
    Color color;
}
