package com.midas.midas_project.domain.employment.dto;

import com.midas.midas_project.domain.employment.Employment;
import com.midas.midas_project.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
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
        private String isVisible;
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime recruitStartDatetime;
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime recruitEndDatetime;

        public Employment toEntity() {
            return Employment.builder()
                    .position(this.position)
                    .occupation(this.occupation)
                    .assignedTask(this.assignedTask)
                    .qualification(this.qualification)
                    .isVisible(this.isVisible)
                    .recruitStartDatetime(this.recruitStartDatetime)
                    .recruitEndDatetime(this.recruitEndDatetime)
                    .build();
        }
    }

    @Builder
    @Getter
    public static class Put {
        private String position;
        private String occupation;
        private String assignedTask;
        private String qualification;
        private String isVisible;
        private LocalDateTime recruitStartDatetime;
        private LocalDateTime recruitEndDatetime;
    }
}
