package com.midas.midas_project.domain.user;

import com.midas.midas_project.domain.user.dto.UserRequestDto;
import com.midas.midas_project.domain.user.dto.UserResponseDto;
import com.midas.midas_project.domain.userlog.UserLogService;
import com.midas.midas_project.domain.userlog.dto.UserLogDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "관리자 관리 API")
@RequiredArgsConstructor
@RequestMapping(value = "/admin")
@RestController
public class UserController {

    private final UserService userService;
    private final UserLogService userLogService;

    @PostMapping("/users")
    public UserResponseDto createUser(@RequestBody UserRequestDto.Post post) {
        return userService.saveUser(post);
    }

    @GetMapping("/users/{userId}")
    public UserResponseDto getUser(@PathVariable Long userId) {
        return userService.selectUser(userId);
    }

    @GetMapping("/users")
    public List<UserResponseDto> getUserList(Pageable pageable) {
        return userService.selectUserList(pageable);
    }

    @PutMapping("/users/{userId}")
    public UserResponseDto updateUser(@PathVariable long userId, @RequestBody UserRequestDto.Put update) {
        return userService.updateUser(userId, update);
    }

    @DeleteMapping("/users/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

}
