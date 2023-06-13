package com.midas.midas_project.infra.enums;

import lombok.Getter;

@Getter
public enum LoginType {

    LOGIN_SUCCESS("Y", true),
    LOGIN_FAILED("N", false);

    private String code;
    private boolean isSuccess;

    LoginType(String code, boolean isSuccess) {
        this.code = code;
        this.isSuccess = isSuccess;
    }


}
