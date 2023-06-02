package com.midas.midas_project.domain.user;

import com.midas.midas_project.domain.user.dto.UserLoginDto;
import com.midas.midas_project.domain.user.dto.UserRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping(value = "/admin/users")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping
    public void createUser(@RequestBody UserRequestDto userRequestDto) {
        userService.saveUser(userRequestDto);
    }

    @GetMapping("/{userId}")
    public void getUser(@PathVariable String userId) {

    }

    @GetMapping
    public void getUserList() {

    }

    @PutMapping("/{userId}")
    public void updateUser(@PathVariable String userId) {

    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable String userId) {

    }
}
