package com.midas.midas_project.domain.user;

import com.midas.midas_project.domain.user.dto.UserResponseDto;
import com.midas.midas_project.model.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
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
    private long userId;

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

    @Column(name = "role", length = 20)
    @Comment("권한")
    private String role;

    public UserResponseDto toDto(User user) {
        ModelMapper modelMapper = new ModelMapper(); // 빈 등록하여 관리
        return modelMapper.map(user, UserResponseDto.class);
    }
// 리플렉션 제너럴 Tㅌ타입으로 받아서 원하는것만 한번에 수정하도록
    public User patch(Map<String, Object> patchMap) {
        for(Map.Entry<String,Object> entry : patchMap.entrySet()){
            findKeyAndPatch(entry.getKey(), entry.getValue());
        }
        return this;
    }

    public void findKeyAndPatch(String key, Object value) {
        switch (key) {
            case "userName":
                this.userName = value.toString();
                break;
            case "team":
                this.team = value.toString();
                break;
            case "phone":
                this.phone = value.toString();
                break;
            case "userRoles":
                    break;
            default:
                throw new IllegalArgumentException("해당 column이 없습니다." + key);
        }
    }

}
