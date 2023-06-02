package com.midas.midas_project.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;

    //권한 리스트 저장
    public void saveUserRole(List<String> userRoles, User user) {
        for(String role : userRoles) { // stream 사용할 것
            userRoleRepository.save(UserRole.builder().url(role).user(user).build());
        }
    }
}
