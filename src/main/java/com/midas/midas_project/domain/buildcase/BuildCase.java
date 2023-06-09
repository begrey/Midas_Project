package com.midas.midas_project.domain.buildcase;

import com.midas.midas_project.domain.category.Category;
import com.midas.midas_project.domain.user.UserRole;
import com.midas.midas_project.model.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="build_case")
public class BuildCase extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "build_case_id")
    @Comment("구축 사례 아이디")
    private long buildCaseId;

    @Column(name = "build_case_name", nullable = false, length = 12)
    @Comment("사례 이름")
    private String buildCaseName;

    @Column(name = "is_visible", nullable = false, length = 12)
    @Comment("노출 여부")
    private String isVisible;

    @OneToMany(mappedBy = "buildCase", fetch = FetchType.LAZY)
    @Column
    @Comment("구축사례 파일 아이디")
    private List<BuildCaseFile> buildCaseFiles = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "build_case_ibfk_1"))
    @Comment("카테고리 아이디")
    private Category category;

    @OneToMany(mappedBy = "buildCase", fetch = FetchType.LAZY)
    @Column
    @Comment("구축사례 테이블 아이디")
    private List<BuildCaseTable> buildCaseTables = new ArrayList<>();

    @OneToMany(mappedBy = "buildCase", fetch = FetchType.LAZY)
    @Column
    @Comment("구축사례 배너 아이디")
    private List<BuildCaseBanner> buildCaseBanners = new ArrayList<>();

    public void setBuildCaseFilesFile(BuildCaseFile file) {
        buildCaseFiles.add(file);
    }


    public void setBanner() {

    }
}
