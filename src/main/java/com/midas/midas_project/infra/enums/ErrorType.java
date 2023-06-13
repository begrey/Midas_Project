package com.midas.midas_project.infra.enums;

import lombok.Getter;

@Getter
public enum ErrorType {
    EXPIRED_TOKEN("만료된 토큰"),
    NOT_VALID_JWS_ARGUMENT("유효하지 않은 JWS 인자"),
    JWS_SIGNATURE_INVALID("유효하지 않은 서명"),
    NOT_FOUND_USER("해당 유저가 존재하지 않음"),
    NOT_FOUND_TOKEN("헤더에 토큰이 존재하지 않음");

    private String detail;
    ErrorType(String detail) {
        this.detail = detail;
    }
}
