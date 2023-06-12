package com.midas.midas_project.domain.buildcase;

import com.midas.midas_project.domain.employment.Employment;
import com.midas.midas_project.model.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="build_case_banner")
public class BuildCaseBanner extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "build_case_banner_id")
    @Comment("구축사례 배너 아이디")
    private Long buildCaseBannerId;

    @Column(name = "title", nullable = false, length = 50)
    @Comment("제목")
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "build_case_id", foreignKey = @ForeignKey(name = "build_case_table_ibfk_1"))
    @Comment("구축사례 아이디")
    private BuildCase buildCase;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "build_case_file_id", foreignKey = @ForeignKey(name = "build_case_banner_ibfk_2"))
    @Comment("구축사례 파일 아이디")
    private BuildCaseFile buildCaseFile;

    public static BuildCaseBanner toEntity(BuildCase buildCase, String title, BuildCaseFile buildCaseFile) {
        return BuildCaseBanner.builder()
                .title(title)
                .buildCaseFile(buildCaseFile)
                .buildCase(buildCase)
                .build();
    }

    public void setBuildCaseFile(BuildCaseFile buildCaseFile) {
        this.buildCaseFile = buildCaseFile;
    }

}
