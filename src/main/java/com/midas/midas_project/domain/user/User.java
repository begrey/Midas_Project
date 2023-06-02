package com.midas.midas_project.domain.user;

import com.midas.midas_project.model.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name="user",
        uniqueConstraints = {@UniqueConstraint(name="uk_user_001", columnNames = "phone"),
    })
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", length = 12)
    @Comment("유저 아이디")
    private String userId;

    @Column(name = "password", nullable = false, length = 12)
    @Comment("비밀번호")
    private String password;

    @Column(name = "user_name", nullable = false, length = 12)
    @Comment("성명")
    private String userName;

    @Column(name = "team", length = 12)
    @Comment("소속")
    private String team;

    @Column(name = "phone", length = 12)
    @Comment("연락처")
    private String phone;


}
