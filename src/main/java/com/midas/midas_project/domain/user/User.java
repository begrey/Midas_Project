package com.midas.midas_project.domain.user;

import com.midas.midas_project.domain.user.dto.UserRequestDto;
import com.midas.midas_project.domain.user.dto.UserResponseDto;
import com.midas.midas_project.infra.enums.AdminType;
import com.midas.midas_project.model.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    @Column(name = "user_id")
    @Comment("유저 아이디")
    private Long userId;

    @Column(name = "midas_user_id", nullable = false, length = 12)
    @Comment("유저 가입 아이디")
    private String midasUserId;

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

    @Column(name = "admin_type", length = 20)
    @Comment("권한")
    @Enumerated(EnumType.STRING)
    private AdminType adminType;

    @Builder.Default
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @Column
    private List<UserRole> userRoles = new ArrayList<>();

    public void setUserRoles (List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }



    public User update(UserRequestDto.Put update) {
        this.team = update.getTeam();
        this.phone = update.getPhone();
        this.adminType = update.getAdminType();
        this.userName = update.getUserName();
        return this;
    }


}
