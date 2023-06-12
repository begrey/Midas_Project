package com.midas.midas_project.domain.buildcase.dto;

import com.midas.midas_project.domain.buildcase.BuildCase;
import com.midas.midas_project.domain.buildcase.BuildCaseBanner;
import com.midas.midas_project.domain.buildcase.BuildCaseTable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "구축 사례 응답 객체")
public class BuildcaseResponseDto {
    private String buildCaseName;
    private String isVisible;
    private Long categoryId;
    private String thumbnailSrc;
    private List<String> detailSrcs = new ArrayList<>();
    private List<BuildCaseTableDto> tables;
    private List<BuildCaseBannerDto> banners;

    @Builder
    @Getter
    public static class BuildCaseTableDto {
        private String title;
        private String content;
    }

    public static List<BuildCaseTableDto> toTableDto(List<BuildCaseTable> tables) {
        return tables.stream()
                .map(table -> new BuildCaseTableDto(table.getTitle(), table.getContent()))
                .toList();
    }

    @Builder
    @Getter
    public static class BuildCaseBannerDto {
        private String title;
        private String bannerSrc;
    }

    public static List<BuildCaseBannerDto> toBannerDto(List<BuildCaseBanner> banners) {
        return banners.stream()
                .map(banner -> new BuildCaseBannerDto(banner.getTitle(), banner.getBuildCaseFile().getFilePath()))
                .toList();
    }

    public static BuildcaseResponseDto toDto(BuildCase buildCase) {
        BuildcaseResponseDto dto = BuildcaseResponseDto.builder()
                .buildCaseName(buildCase.getBuildCaseName())
                .isVisible(buildCase.getIsVisible())
                .categoryId(buildCase.getCategory().getCategoryId())
                        .build();
        buildCase.getBuildCaseFiles().forEach(
                file -> {
                    if (file.getFileType().equals("thumbnail")) {
                        dto.setThumbnailSrc(file.getFilePath());
                    } else if (file.getFileType().equals("detail")){
                        if (dto.getDetailSrcs() == null) dto.setDetailSrcs(new ArrayList<>());
                        dto.getDetailSrcs().add(file.getFilePath());
                    }
                }
        );
        if (!ObjectUtils.isEmpty(buildCase.getBuildCaseTables())) {
            dto.setTables(toTableDto(buildCase.getBuildCaseTables()));
        }
        if (!ObjectUtils.isEmpty(buildCase.getBuildCaseBanners())) {
            dto.setBanners(toBannerDto(buildCase.getBuildCaseBanners()));
        }
        return dto;
    }
}
