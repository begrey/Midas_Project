package com.midas.midas_project.infra.security;

import com.midas.midas_project.domain.user.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

    private final UserRoleService userRoleService;

    private final UserRepository userRepository;

    @Value("${jwt.secret}")
    private String secret;

    //아이디 가져오기
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //토큰 만료일 가져오기
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //토큰으로부터 어떤 정보를 가져오더라도 시크릿 키가 필요하다 (클레임 : 정보의 한 ‘조각’)
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    //토큰 만료일 체크 (milisecond 기준)
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // Request의 Header에서 token 값을 가져옵니다. "X-AUTH-TOKEN" : "TOKEN값'
    public String resolveToken(HttpServletRequest req) {
//        Cookie[] cookies = req.getCookies();
//        String header = null;
//        if (cookies != null)
//            for (Cookie c : cookies)
//                if (c.getName().equals("Authorization"))
//                    header = "Bearer " + c.getValue();
        final String requestTokenHeader = req.getHeader("Authorization");
        // only the Token
        String username = null;
        String jwtToken = null;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
            }
        } else {
            //System.out.println("requestTokenHeader : " + requestTokenHeader);
        }
        return jwtToken;
        //return req.getHeader("X-AUTH-TOKEN");@Component
        //public class WebAccessDeniedHandler implements AccessDeniedHandler
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        Claims claims = getAllClaimsFromToken(token);
        UserDetails userDetails = userDetailService.loadUserByUsername(this.getUsernameFromToken(token));
        User user = userRepository.findByMidasUserIdAndPassword(userDetails.getUsername(), userDetails.getPassword());
        List<String> userRoleList = userRoleService.selectUserRoleUrlList(user.getUserId());
        return new UsernamePasswordAuthenticationToken(userDetails, userRoleList, userDetails.getAuthorities());
    }

    //유저를 위한 토큰 생성
    public String generateToken(String userId, String password, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("role", role);
        claims.put("password", password);
        return doGenerateToken(claims, userId);
    }

    // 토근이 만들어지는 과정
    //1. id, subjcet(토큰 제목), expiration, issuer(토큰 발급자)등 토큰의 클레임들을 정의한다.
    //2. Jwt를 HS512 algorithm 과 secret key로 sign(서명)한다.
    //3. JWS Compact Serialization 방식에 따라 jwt의 압축본이 url에 안전한 스트링으로 변환된다.
    // sign(서명) : 헤더의 인코딩값과, 정보의 인코딩값을 합친후 주어진 비밀키로 해쉬를 하여 생성
    private String doGenerateToken(Map<String, Object> claims, String userId) {

        return Jwts.builder().setClaims(claims).setSubject(userId).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    //토큰 유효성 체크
    public Boolean validateToken(String token) {
        return (!isTokenExpired(token));
    }
}
