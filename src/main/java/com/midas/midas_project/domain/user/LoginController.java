package com.midas.midas_project.domain.user;

import com.midas.midas_project.domain.user.dto.UserLoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@RequestMapping(value = "/login")
@RestController
public class LoginController {
    @PostMapping("/login")
    public void login(UserLoginDto userLoginDto, HttpServletRequest request, HttpServletResponse response) throws IOException {
//        JwtResponseDto jwt = loginService.login(userSignInDto);
//        setCookieAndRedirectMain(jwt, request, response);
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
        return "redirect:/main";
    }
}
