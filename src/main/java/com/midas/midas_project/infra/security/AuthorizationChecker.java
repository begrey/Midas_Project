package com.midas.midas_project.infra.security;

import com.midas.midas_project.infra.enums.RoleType;
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
        List<String> roleUrlList = (List<String>) authentication.getCredentials();
        for(String url : roleUrlList) {
            log.info(RoleType.of(url).getUrl() + "   :::   " + request.getRequestURI());
            if (new AntPathMatcher().match(RoleType.of(url).getUrl(), request.getRequestURI())) {
                return true;
            }
        }
        return false;
    }
}
