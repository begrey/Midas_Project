package com.midas.midas_project.domain.userlog;

import com.midas.midas_project.domain.userlog.dto.UserLogDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "관리자 로그 API")
@RequiredArgsConstructor
@RequestMapping(value = "/admin")
@RestController
public class UserLogController {
    private final UserLogService userLogService;

    @GetMapping("/logs")
    public List<UserLogDto> getUserLogList(Pageable pageable) {
        return userLogService.selectUserLogList(pageable);
    }
    @GetMapping("/logs/search")
    public List<UserLogDto> SearchUserLogListByUserId(Pageable pageable, @RequestParam("id") String midasUserId) {
        return userLogService.searchUserLogListById(pageable, midasUserId);
    }
}
