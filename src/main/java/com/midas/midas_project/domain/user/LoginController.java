package com.midas.midas_project.domain.user;

import com.midas.midas_project.domain.user.dto.LoginRequestDto;
import com.midas.midas_project.domain.user.dto.LoginResponseDto;
import com.midas.midas_project.domain.userlog.UserLogService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Tag(name = "로그인/로그아웃 API")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/users")
public class LoginController {

    private final LoginService loginService;

    private final UserLogService userLogService;
    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto userLoginDto, HttpServletRequest request, HttpServletResponse response) throws IOException {
        LoginResponseDto dto = null;
        boolean loginSuccess = true;
        try {
            dto = loginService.login(userLoginDto, request);
        } catch (Exception e) {
            loginSuccess = false;
        }
        userLogService.createUserLog(request, userLoginDto.getUserId(), loginSuccess);
        return dto;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {//
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals("Authorization") || cookie.getName().equals("userId")
//                        || cookie.getName().equals("userName") || cookie.getName().equals("JSESSIONID")) {
//                    cookie.setMaxAge(0); // 쿠키의 expiration 타임을 0으로 하여 없앤다.
//                    cookie.setPath("/");
//                    response.addCookie(cookie);
//                }
//            }
//        }
//        else
//            return "login/login";
        return "logout successed";
    }
}
