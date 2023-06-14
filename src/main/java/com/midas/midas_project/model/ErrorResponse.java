package com.midas.midas_project.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.midas.midas_project.infra.enums.ErrorType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public final class ErrorResponse {

    @Schema(description = "발생 시간")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    LocalDateTime timestamp;

    @Schema(description = "상태")
    HttpStatus status;

    @Schema(description = "에러 메시지")
    String detail;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "에러 코드")
    ErrorType errorType;

    public static ErrorResponse of(HttpStatus httpStatus, String detail, ErrorType errorType) {
        return new ErrorResponse(LocalDateTime.now(), httpStatus, detail, errorType);
    }
}
