package com.midas.midas_project.domain.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.midas.midas_project.domain.user.dto.UserRequestDto;
import com.midas.midas_project.domain.user.dto.UserResponseDto;
import com.midas.midas_project.infra.config.ModelMapperConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    private final UserRoleRepository userRoleRepository;

    private final ModelMapper modelMapper;

    // User 저장
    @Transactional
    public UserResponseDto saveUser(UserRequestDto.Post post) {
        User userEntity = userRepository.save(post.toEntity());
        // UserRole save
        List<UserRole> userRoles = post.getUserRoles().stream()
                .map(url -> userRoleRepository.save(UserRole.toEntity(userEntity, url))).toList();
        userEntity.setUserRoles(userRoles);
        return UserResponseDto.toDto(userEntity, userRoles, modelMapper);
    }

    // User 수정
    @Transactional
    public UserResponseDto updateUser(Long userId, UserRequestDto.Put update) {

        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 User가 존재하지 않음"));
        User patchUser = user.update(update);

        List<UserRole> userRoles = new ArrayList<>();
        // 유저 저장 성공 시 관련 권한도 수정 -> 기존 권한 삭제 후 새로운 권한 변경
        if (!ObjectUtils.isEmpty(update.getUserRoles())) {
            userRoleRepository.deleteAllByUserUserId(user.getUserId());
            userRoles = update.getUserRoles().stream()
                    .map(url -> userRoleRepository.save(UserRole.toEntity(patchUser, url))).toList();
            patchUser.setUserRoles(userRoles);
        }
        return UserResponseDto.toDto(patchUser, userRoles, modelMapper);
    }

    //User 조회
    public UserResponseDto selectUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 User가 존재하지 않음")); // 예외처리
        List<UserRole> userRoles = userRoleRepository.findByUserUserId(userId);
        return UserResponseDto.toDto(user, userRoles, modelMapper);
    }

    //User 리스트 조회
    public List<UserResponseDto> selectUserList(Pageable pageable) {
        Page<User> userList = userRepository.findAll(pageable);
        return userList.stream()
                .map(user -> UserResponseDto.toDto(user, userRoleRepository.findByUserUserId(user.getUserId()), modelMapper)).toList();
    }

    @Transactional
    public void deleteUser(Long userId) {
        userRoleRepository.deleteAllByUserUserId(userId);
        userRepository.deleteById(userId);
    }
}
