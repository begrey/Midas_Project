package com.midas.midas_project.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    private String userName;
    private String team;
    private String password;
    private String phone;
    List<String> userRoles; // 차후에 ENUM을 통해 role 관리
}
