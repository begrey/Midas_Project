package com.midas.midas_project.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;

    //권한 리스트 저장
    public List<UserRole> saveUserRole(List<String> userRoles, User user) {
        List<UserRole> userRoleList = new ArrayList<>();
        for(String role : userRoles) { // stream 사용할 것
            userRoleList.add(UserRole.builder().url(role).user(user).build());
        }
       return userRoleRepository.saveAll(userRoleList);
    }

    //권한 리스트 전체 삭제
    public void deleteAllByUser(User user) {
        userRoleRepository.deleteAllByUser(user);
    }

    //권한 리스트 전체 조회
    public List<UserRole> selectUserRoleList(User user) {
        return userRoleRepository.findByUser_userId(user.getUserId());
    }

    //권한 url 리스트 조회
    public List<String> selectUserRoleUrlList(Long userId) {
        return userRoleRepository.findByUser_userId(userId)
                .stream()
                .map(entity -> entity.getUrl())
                .collect(Collectors.toList());
    }

}
