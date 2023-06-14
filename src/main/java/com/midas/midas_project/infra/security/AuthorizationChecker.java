package com.midas.midas_project.infra.security;

import com.midas.midas_project.infra.enums.ErrorType;
import com.midas.midas_project.infra.enums.RoleType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Component
public class AuthorizationChecker {

    /*
        사용자 별 권한 URL을 가져와 인가 영역 체크
     */

    public boolean checkRole(HttpServletRequest request, Authentication authentication) {
        // 이미 exception(인증오류)이 있는 경우
        if (!ObjectUtils.isEmpty((request.getAttribute("exception")))) {
            return false;
        }

        List<String> roleUrlList = (List<String>) authentication.getCredentials();
        for(String url : roleUrlList) {
            if (new AntPathMatcher().match(RoleType.of(url).getUrl(), request.getRequestURI())) {
                return true;
            }
        }
        request.setAttribute("exception", ErrorType.ACCESS_DENIED);
        return false;
    }
}
