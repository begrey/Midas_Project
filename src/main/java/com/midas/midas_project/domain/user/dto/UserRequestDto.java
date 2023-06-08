package com.midas.midas_project.domain.user.dto;

import com.midas.midas_project.domain.user.User;
import com.midas.midas_project.domain.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


public class UserRequestDto {

    @Builder
    @Getter
    public static class Post {
        private String userName;
        private String midasUserId;
        private String team;
        private String password;
        private String phone;
        private String role;
        List<String> userRoles; // 차후에 ENUM을 통해 role 관리

        public User toEntity() {
            return User.builder()
                    .userName(this.userName)
                    .midasUserId(this.midasUserId)
                    .team(this.team)
                    .password(this.password)
                    .phone(this.phone)
                    .role(this.role)
                    .build();
        }
    }
    @Builder
    @Getter
    public static class Put {
        private String userName;
        private String midasUserId;
        private String team;
        private String password;
        private String phone;
        private String role;
        List<String> userRoles; // 차후에 ENUM을 통해 role 관리

    }

}
