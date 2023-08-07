package com.sparta.hotsixproject.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseDto {
    private Integer httpStatusCode;
    private String message;

    public ApiResponseDto(Integer httpStatusCode,String message) {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
    }
}
