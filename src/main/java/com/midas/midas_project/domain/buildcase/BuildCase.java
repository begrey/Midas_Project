package com.midas.midas_project.domain.buildcase;

import com.midas.midas_project.domain.user.UserRole;
import com.midas.midas_project.model.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="build_case")
public class BuildCase extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "build_case_id")
    @Comment("유저 아이디")
    private long userId;

    @Column(name = "build_case_name", nullable = false, length = 12)
    @Comment("유저 가입 아이디")
    private String midasUserId;

    @Column(name = "is_visible", nullable = false, length = 12)
    @Comment("비밀번호")
    private String password;

    @Column(name = "description", nullable = false, length = 12)
    @Comment("성명")
    private String userName;


    @Builder.Default
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @Column
    private List<UserRole> userRoles = new ArrayList<>();
}
