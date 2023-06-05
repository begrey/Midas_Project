package com.midas.midas_project.infra.security;

import com.midas.midas_project.domain.user.User;
import com.midas.midas_project.domain.user.UserRepository;
import com.midas.midas_project.domain.user.UserRole;
import com.midas.midas_project.domain.user.UserRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Component
public class AuthorizationChecker {

    public boolean check(HttpServletRequest request, Authentication authentication) {
        Object principalObj = authentication.getPrincipal();
        UserDetails userDetails = (UserDetails) principalObj;

        List<String> roleUrlList = (List<String>) authentication.getCredentials();
        for(String url : roleUrlList) {
            log.info(url + "   :::   " + request.getRequestURI());
            if (new AntPathMatcher().match(url, request.getRequestURI())) {
                return true;
            }
        }
        return false;
    }
}
