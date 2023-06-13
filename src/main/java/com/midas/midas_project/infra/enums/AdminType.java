package com.midas.midas_project.infra.enums;

import lombok.Getter;

@Getter
public enum AdminType {
    ROLE_MASTER("MASTER"),
    ROLE_ADMIN("ADMIN");

    private String role;

    AdminType(String role) {
        this.role = role;
    }
}
