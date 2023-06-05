package com.midas.midas_project.domain.user;

import com.midas.midas_project.domain.user.dto.UserRequestDto;
import com.midas.midas_project.domain.user.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping(value = "/admin/users")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserResponseDto createUser(@RequestBody UserRequestDto userRequestDto) {
        return userService.saveUser(userRequestDto);
    }

    @GetMapping("/{userId}")
    public UserResponseDto getUser(@PathVariable Long userId) {
        return userService.selectUser(userId);
    }

    @GetMapping
    public List<UserResponseDto> getUserList() {
        return userService.selectUserList();
    }

    @PutMapping("/{userId}")
    public UserResponseDto updateUser(@PathVariable long userId, @RequestBody UserRequestDto userRequestDto) {
        return userService.updateUser(userId, userRequestDto);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}
