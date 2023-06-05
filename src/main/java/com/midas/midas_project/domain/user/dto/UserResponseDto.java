package com.midas.midas_project.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.midas.midas_project.domain.user.User;
import com.midas.midas_project.domain.user.UserRole;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto{
    private Long userId;
    private String userName;
    private String midasUserId;
    private String team;
    private String password;
    private String phone;
    private String role;
    List<String> userRoleUrls; // 반환값은 url값만 , 차후에 ENUM을 통해 role 관리

}

