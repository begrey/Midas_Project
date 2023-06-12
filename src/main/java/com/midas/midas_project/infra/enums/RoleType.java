package com.midas.midas_project.infra.enums;

import lombok.Getter;

@Getter
public enum RoleType {
    ROLE_ADMIN("ADMIN", "/admin/**"),
    ROLE_EMPLOYMENT("EMPLOYMENT", "/employment/**"),
    ROLE_BUILD_UPS_STS("UPS&STS", "/build-case/UPS&STS/**"),
    ROLE_BUILD_COOLING("COOLING", "/build-case/COOLING/**"),
    ROLE_BUILD_LIGHTING("LIGHTING", "/build-case/LIGHTING/**"),
    ROLE_BUILD_RAILROAD("RAILROAD", "/build-case/RAILROAD/**");

    private String role;
    private String url;

    RoleType(String role, String url) {
        this.role = role;
        this.url = url;
    }
    public String returnRole() {
        return role;
    }
    public String returnUrl() {return url;}


}
