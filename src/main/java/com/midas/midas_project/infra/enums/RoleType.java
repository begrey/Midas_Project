package com.midas.midas_project.infra.enums;

import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    private static final Map<String, String> ROLE_MAP = Collections.unmodifiableMap(
            Stream.of(values()).collect(Collectors.toMap(RoleType::getRole, RoleType::name)));
    public static RoleType of(final String role) {
        return RoleType.valueOf(ROLE_MAP.get(role));
    }

    RoleType(String role, String url) {
        this.role = role;
        this.url = url;
    }
    public String returnRole() {
        return role;
    }
    public String returnUrl() {return url;}


}
