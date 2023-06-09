package com.midas.midas_project.domain.userlog;


import com.midas.midas_project.domain.userlog.dto.UserLogDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserLogService {

    private final UserLogRepository userLogRepository;

    private final ModelMapper modelMapper;

    public void createUserLog(HttpServletRequest request, String midasUserId, boolean loginSuccess) {
        String sessionId = null;
        if (request.getCookies() != null) {
            sessionId = Arrays.stream(request.getCookies())
                    .filter(cookie -> cookie != null && cookie.getName().equals("JSESSIONID"))
                    .map(cookie -> cookie.getValue())
                    .findFirst().orElse(null);
        }
        String ipAddress = request.getRemoteAddr();

        userLogRepository.save(UserLog.of(midasUserId, ipAddress, sessionId, loginSuccess));
    }

    public List<UserLogDto> selectUserLogList(Pageable pageable) {
        Page<UserLog> userLogList = userLogRepository.findAll(pageable);

       return userLogList.stream()
                .map(userLog -> UserLogDto.toDto(userLog, modelMapper)).toList();
    }

    public List<UserLogDto> searchUserLogListById(Pageable pageable, String midasUserId) {
        Page<UserLog> userLogList = userLogRepository.findByMidasUserId(midasUserId, pageable);

        return userLogList.stream()
                .map(userLog -> UserLogDto.toDto(userLog, modelMapper)).toList();
    }
}
