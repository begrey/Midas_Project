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
@Table(name="user_role")
public class UserRole extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", length = 12)
    @Comment("권한 아이디")
    private Long roleId;

    @Column(name = "url", nullable = false, length = 100)
    @Comment("권한 체크 대상 url")
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "user_role_ibfk_1"))
    @Comment("유저 아이디")
    private User user;


}
