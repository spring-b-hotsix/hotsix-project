package com.sparta.hotsixproject.board.dto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)  // 응답 값으로 파싱이 될 때, 변환이 될 때 JSON 형태로 변환이 되는데 그 때 non_null인 값들만 반환!!
public class ApiResponseDto {
	private String message;
	private Integer httpStatusCode;

	public ApiResponseDto(String message, Integer httpStatusCode) {
		this.message = message;
		this.httpStatusCode = httpStatusCode;
	}


}
