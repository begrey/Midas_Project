package com.midas.midas_project.domain.userlog.dto;

import com.midas.midas_project.domain.user.User;
import com.midas.midas_project.domain.user.UserRole;
import com.midas.midas_project.domain.user.dto.UserResponseDto;
import com.midas.midas_project.domain.userlog.UserLog;
import com.midas.midas_project.infra.enums.LoginType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "관리자 로그 응답 객체")
public class UserLogDto {
    private String midasUserId;
    private String ipAddress;
    private String sessionId;
    private String isLoginSuccess;
    private LocalDateTime loginDateTime;

    public static UserLogDto toDto(UserLog userLog, ModelMapper modelMapper) {
        UserLogDto dto = modelMapper.map(userLog, UserLogDto.class);
        return dto;
    }
}
