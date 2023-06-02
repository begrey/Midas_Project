package com.midas.midas_project.domain.user;

import com.midas.midas_project.domain.user.dto.UserRequestDto;
import com.midas.midas_project.domain.user.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserRoleService userRoleService;

    public void saveUser(UserRequestDto userRequestDto) {
        User user = User.builder()
                .userName(userRequestDto.getUserName())
                .password(userRequestDto.getPassword()) // 차후 암호화 로직 필요
                .team(userRequestDto.getTeam())
                .phone(userRequestDto.getPhone())
                .build();
        User userEntity = userRepository.save(user);

        // 유저 저장 성공 시 관련 권한도 등록
        if (!ObjectUtils.isEmpty(userRequestDto.getUserRoles())) {
            userRoleService.saveUserRole(userRequestDto.getUserRoles(), userEntity);
        }
    }

    public UserResponseDto selectUser(String userId) {
        Optional<User> user = userRepository.findById(userId); // 예외처리
        ModelMapper modelMapper = new ModelMapper(); // 빈 등록하여 관리
        return modelMapper.map(user.get(), UserResponseDto.class);
    }

    public List<UserResponseDto> selectUserList() {
        return null;
//        List<User> userList = userRepository.findAll();
//        return userList.stream().map(UserResponseDto::new).collect(Collectors.toList());
    }

}
