package com.midas.midas_project.domain.userlog;

import com.midas.midas_project.infra.enums.LoginType;
import com.midas.midas_project.model.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="user_log")
public class UserLog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Comment("로그 아이디")
    private long id;

    @Column(name = "midas_user_id", nullable = false, length = 12)
    @Comment("유저 아이디")
    private String midasUserId;

    @Column(name = "ipAddress", nullable = true, length = 20)
    @Comment("아이피 주소")
    private String ipAddress;

    @Column(name = "session_id", nullable = true, length = 50)
    @Comment("세션 아이디")
    private String sessionId;

    @Column(name = "is_login_success", nullable = false, length = 1)
    @Comment("로그인 성공여부")
    private String isLoginSuccess;

    @CreatedDate
    @CreationTimestamp
    @Column(name = "login_datetime", nullable = false, length = 50)
    @Comment("로그인 일자")
    private LocalDateTime loginDatetime;


    public static UserLog of(String midasUserId, String ipAddress, String sessionId, boolean loginSuccess) {
        LoginType loginType = loginSuccess == true ? LoginType.LOGIN_SUCCESS : LoginType.LOGIN_FAILED;
        return UserLog.builder()
                .midasUserId(midasUserId)
                .ipAddress(ipAddress)
                .sessionId(sessionId)
                .isLoginSuccess(loginType.getCode())
                .build();
    }

}
