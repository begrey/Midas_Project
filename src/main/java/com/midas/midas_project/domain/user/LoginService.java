package com.midas.midas_project.domain.user;

import com.midas.midas_project.domain.user.dto.LoginRequestDto;
import com.midas.midas_project.domain.user.dto.LoginResponseDto;
import com.midas.midas_project.infra.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;

    private final JwtTokenProvider jwtTokenProvider;

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByMidasUserIdAndPassword(loginRequestDto.getUserId(), loginRequestDto.getPassword());
        log.info(loginRequestDto.getUserId() + " " + loginRequestDto.getPassword());
        if (user == null) {
            throw new IllegalArgumentException("회원이 존재하지 않음");
        }
        String token = jwtTokenProvider.generateToken(user.getMidasUserId(), user.getPassword(), user.getRole());
        return new LoginResponseDto(token, user.getMidasUserId());
    }
}
