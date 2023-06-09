package com.midas.midas_project.domain.buildcase.dto;

import com.midas.midas_project.domain.buildcase.BuildCase;
import com.midas.midas_project.domain.category.Category;
import com.midas.midas_project.domain.employment.Employment;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class BuildcaseRequestDto {
    @Builder
    @Getter
    public static class Post {
        private String buildCaseName;
        private String isVisible;
        private Long categoryId;

        public BuildCase toEntity(Category category) {
            return BuildCase.builder()
                    .buildCaseName(this.getBuildCaseName())
                    .isVisible(this.getIsVisible())
                    .category(category)
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
