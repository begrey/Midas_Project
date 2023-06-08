package com.midas.midas_project.domain.employment;

import com.midas.midas_project.domain.user.User;
import com.midas.midas_project.model.BaseEntity;
import lombok.*;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.annotations.Comment;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="employment_file")
public class EmploymentFile extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    @Comment("파일 아이디")
    private Long fileId;

    @Column(name = "file_path", nullable = false, length = 50)
    @Comment("파일 경로")
    private String filePath;

    @Column(name = "real_name", nullable = false, length = 50)
    @Comment("파일 원본명")
    private String realName;

    @Column(name = "temp_name", nullable = false, length = 36)
    @Comment("파일 임시명")
    private String tempName;

    @Column(name = "extension", nullable = false, length = 10)
    @Comment("확장자")
    private String extension;

    @Column(name = "file_size", nullable = false)
    @Comment("파일 크기")
    private Long fileSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employment_id", foreignKey = @ForeignKey(name = "employment_file_ibfk_1"))
    @Comment("채용공고 아이디")
    private Employment employment;


    public static EmploymentFile toEntity(MultipartFile file, String filePath) {
        String filename = file.getOriginalFilename();
        return EmploymentFile.builder()
                .filePath(filePath)
                .realName(filename)
                .tempName(UUID.randomUUID().toString())
                .extension(FilenameUtils.getExtension(filename))
                .fileSize(file.getSize())
                .build();
    }
}
