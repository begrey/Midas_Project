package com.midas.midas_project.domain.buildcase;

import com.midas.midas_project.domain.buildcase.dto.BuildcaseRequestDto;
import com.midas.midas_project.domain.buildcase.dto.BuildcaseResponseDto;
import com.midas.midas_project.domain.employment.dto.EmploymentRequestDto;
import com.midas.midas_project.domain.employment.dto.EmploymentResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Tag(name = "구축사례 API")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/build-case/{category}")
public class BuildCaseController {
    private final BuildCaseService buildCaseService;
    private final BuildCaseFileService buildCaseFileService;
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BuildcaseResponseDto createBuildCase(@RequestPart BuildcaseRequestDto.Post post,
                                                 @RequestPart(required = true, value = "thumbnail") MultipartFile thumbnail,
                                                 @RequestPart(required = false, value = "detailFiles") List<MultipartFile> detailFiles,
                                                @RequestPart(required = false, value = "bannerFiles") List<MultipartFile> bannerFiles
                                                ) throws IOException {
        return buildCaseService.saveBuildCase(post, thumbnail, detailFiles, bannerFiles);
    }

    @PutMapping(value = "/{buildCaseId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BuildcaseResponseDto updateEmployment(@RequestPart BuildcaseRequestDto.Put update,
                                                 @RequestPart(required = true, value = "thumbnail") MultipartFile thumbnail,
                                                 @RequestPart(required = false, value = "detailFiles") List<MultipartFile> detailFiles,
                                                 @RequestPart(required = false, value = "bannerFiles") List<MultipartFile> bannerFiles,
                                                 @PathVariable Long employmentId) throws IOException {
        return buildCaseService.updateBuildCase(update, employmentId, thumbnail, detailFiles, bannerFiles);
    }

    @GetMapping
    public List<BuildcaseResponseDto> selectEmploymentList(Pageable pageable) {
        return buildCaseService.selectEmploymentList(pageable);
    }

    @GetMapping("/{buildCaseId}")
    public BuildcaseResponseDto selectEmployment(@PathVariable Long buildCaseId) {
        return buildCaseService.selectEmployment(buildCaseId);
    }

    @DeleteMapping("/{buildCaseId}")
    public String deleteEmployment(@PathVariable Long buildCaseId) {
        buildCaseService.deleteEmployment(buildCaseId);
        return "Delete Completed.";
    }
}
