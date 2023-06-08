package com.midas.midas_project.domain.employment.dto;

import com.midas.midas_project.domain.employment.Employment;
import com.midas.midas_project.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;


public class EmploymentRequestDto {
    @Builder
    @Getter
    public static class Post {
        private String position;
        private String occupation;
        private String assignedTask;
        private String qualification;
        private LocalDateTime recruitStartDatetime;
        private LocalDateTime recruitEndDatetime;
        List<MultipartFile> employmentFiles; // 차후에 ENUM을 통해 role 관리

        public Employment toEntity() {
            return Employment.builder()
                    .position(this.position)
                    .occupation(this.occupation)
                    .assignedTask(this.assignedTask)
                    .qualification(this.qualification)
                    .recruitEndDatetime(this.recruitStartDatetime)
                    .recruitEndDatetime(this.recruitEndDatetime)
                    .build();
        }
    }
}
