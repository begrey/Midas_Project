package com.midas.midas_project.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.midas.midas_project.infra.enums.ErrorType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    @Schema(description = "발생 시간")
    LocalDateTime timestamp;
    @Schema(description = "상태")
    HttpStatus status;
    @Schema(description = "에러 메시지")
    String error;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "에러 코드")
    ErrorType errorType;
}
