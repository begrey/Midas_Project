package com.midas.midas_project.domain.employment;

import com.midas.midas_project.domain.user.UserRole;
import com.midas.midas_project.model.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="employment")
public class Employment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employment_id")
    @Comment("채용공고 아이디")
    private long employmentId;

    @Column(name = "position", nullable = false, length = 20)
    @Comment("모집부문")
    private String position;

    @Column(name = "occupation", nullable = false, length = 20)
    @Comment("채용분야")
    private String occupation;

    @Column(name = "assigned_task", nullable = false, length = 300)
    @Comment("해당업무")
    private String assignedTask;

    @Column(name = "qualification", length = 300)
    @Comment("자격요건")
    private String qualification;

    @Column(name = "recruit_start_datetime")
    @Comment("채용시작일")
    private LocalDateTime recruitStartDatetime;

    @Column(name = "recruit_end_datetime")
    @Comment("채용종료일")
    private LocalDateTime recruitEndDatetime;

    @OneToMany(mappedBy = "employment", fetch = FetchType.LAZY)
    @Column
    private List<EmploymentFile> employmentFiles = new ArrayList<>();
}
