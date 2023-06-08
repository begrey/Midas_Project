package com.midas.midas_project.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.midas.midas_project.domain.user.User;
import com.midas.midas_project.domain.user.UserRole;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;


@Getter
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
    List<String> userRoles; // 반환값은 url값만 , 차후에 ENUM을 통해 role 관리


    public static UserResponseDto toDto(User user, List<UserRole> userRoles, ModelMapper modelMapper) {
        UserResponseDto dto = modelMapper.map(user, UserResponseDto.class);
        dto.setUserRoleUrl(userRoles);
        return dto;
    }

    public void setUserRoleUrl(List<UserRole> userRoles) {
        this.userRoles = userRoles.stream().map(role -> role.getUrl()).toList();
    }

}

