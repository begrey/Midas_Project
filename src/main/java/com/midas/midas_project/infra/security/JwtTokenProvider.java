package com.midas.midas_project.infra.security;

import com.midas.midas_project.domain.user.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    private static final long serialVersionUID = -2550185165626007488L;

    public static final long JWT_TOKEN_VALIDITY = 2 * 60 * 60;

    private final CustomUserDetailService userDetailService;

    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    @Value("${jwt.secret}")
    private String secret;

    //아이디 가져오기
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }
    // 토큰 검증하기
    public String validateToken(String token) {
        String userId = getClaimFromToken(token, Claims::getSubject);
        System.out.println("@@@검증 추가해야함 " + userId);
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return claimsResolver.apply(claims);
    }

    //유저를 위한 토큰 생성
    public String generateToken(String userId, String password, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1))//JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailService.loadUserByUsername(this.getUsernameFromToken(token));
        User user = userRepository.findByMidasUserIdAndPassword(userDetails.getUsername(), userDetails.getPassword());
        List<String> userRoleList = userRoleRepository.findByUserUserId(user.getUserId()).stream().map(role -> role.getUrl()).toList();
        return new UsernamePasswordAuthenticationToken(userDetails, userRoleList, userDetails.getAuthorities());
    }



}
