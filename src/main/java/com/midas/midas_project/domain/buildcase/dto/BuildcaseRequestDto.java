package com.midas.midas_project.domain.buildcase.dto;

import com.midas.midas_project.domain.buildcase.BuildCase;
import com.midas.midas_project.domain.category.Category;
import com.midas.midas_project.domain.employment.Employment;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;


@Schema(description = "구축 사례 요청 객체")
public class BuildcaseRequestDto {
    @Builder
    @Getter
    public static class Post {
        private String buildCaseName;
        private String isVisible;
        private Long categoryId;
        private List<BuildCaseTableDto> tables;
        private List<String> banners;

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
        private String buildCaseName;
        private String isVisible;
        private Long categoryId;
        private List<BuildCaseTableDto> tables;
        private List<String> banners;
    }

    @Builder
    @Getter
    public static class BuildCaseTableDto {
        private String title;
        private String content;
    }

}
