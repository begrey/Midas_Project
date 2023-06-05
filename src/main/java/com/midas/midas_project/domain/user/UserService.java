package com.midas.midas_project.domain.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.midas.midas_project.domain.user.dto.UserRequestDto;
import com.midas.midas_project.domain.user.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private final UserRoleService userRoleService;

    // User 저장
    public UserResponseDto saveUser(UserRequestDto userRequestDto) {
        User user = User.builder()
                .userName(userRequestDto.getUserName())
                .midasUserId(userRequestDto.getMidasUserId())
                .password(userRequestDto.getPassword()) // 차후 암호화 로직 필요
                .team(userRequestDto.getTeam())
                .phone(userRequestDto.getPhone())
                .role(userRequestDto.getRole())
                .build();
        User userEntity = userRepository.save(user);

        ObjectMapper om = new ObjectMapper();
        om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        om.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        om.registerModule(new JavaTimeModule());

        UserResponseDto dto = om.convertValue(userEntity, UserResponseDto.class);

        // 유저 저장 성공 시 관련 권한도 등록
        if (!ObjectUtils.isEmpty(userRequestDto.getUserRoles())) {
            List<UserRole> userRoleList = userRoleService.saveUserRole(userRequestDto.getUserRoles(), userEntity);
            dto.setUserRoleUrls(userRoleList.stream()
                    .map(entity -> entity.getUrl())
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    // User 수정
    @Transactional
    public UserResponseDto updateUser(long userId, UserRequestDto userRequestDto) {
        // 차후 모듈화
        ObjectMapper om = new ObjectMapper();
        om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        om.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        om.registerModule(new JavaTimeModule());

        Map<String, Object> map = om.convertValue(userRequestDto, Map.class);
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 User가 존재하지 않음"));

        User patchUser = user.patch(map);
        UserResponseDto dto = om.convertValue(patchUser, UserResponseDto.class);

        // 유저 저장 성공 시 관련 권한도 수정 -> 기존 권한 삭제 후 새로운 권한 변경?
        if (!ObjectUtils.isEmpty(userRequestDto.getUserRoles())) {
            userRoleService.deleteAllByUser(user);
            List<UserRole> userRoleList = userRoleService.saveUserRole(userRequestDto.getUserRoles(), patchUser);
            dto.setUserRoleUrls(userRoleList.stream()
                    .map(entity -> entity.getUrl())
                    .collect(Collectors.toList()));
            //.toList()로 바로 적용 가능
        }
        return dto;
    }

    //User 조회
    public UserResponseDto selectUser(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 User가 존재하지 않음")); // 예외처리
        UserResponseDto userResponseDto = user.toDto(user);
        //해당 유저 권한 리스트 조회
        List<UserRole> userRoleList = userRoleService.selectUserRoleList(user);
        userResponseDto.setUserRoleUrls(userRoleList.stream()
                .map(entity -> entity.getUrl())
                .toList());
        return userResponseDto;
    }

    //User 리스트 조회
    public List<UserResponseDto> selectUserList() {
        List<User> userList = userRepository.findAll();
        List<UserResponseDto> dtoList = userList.stream()
                .map(user -> user.toDto(user))
                .collect(Collectors.toList());

        dtoList.stream().forEach(dto -> dto.setUserRoleUrls(userRoleService.selectUserRoleUrlList(dto.getUserId())));
        // stream의 for-each는 의미 없음

        return dtoList;
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
