package com.midas.midas_project.domain.buildcase;

import com.midas.midas_project.domain.buildcase.dto.BuildcaseRequestDto;
import com.midas.midas_project.domain.buildcase.dto.BuildcaseResponseDto;
import com.midas.midas_project.domain.employment.dto.EmploymentRequestDto;
import com.midas.midas_project.domain.employment.dto.EmploymentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/build-case")
public class BuildCaseController {
    private final BuildCaseService buildCaseService;

//    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public BuildcaseResponseDto createEmployment(@RequestPart BuildcaseRequestDto.Post post,
//                                                  @RequestPart(required = false, value = "file") MultipartFile employmentThumbnail) throws IOException {
//        return buildCaseService.saveBuildCase(post, employmentThumbnail);
//    }
//
//    @PutMapping(value = "/{buildCaseId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public BuildcaseResponseDto updateEmployment(@RequestPart BuildcaseRequestDto.Put update,
//                                                 @RequestPart(required = false, value = "file") MultipartFile employmentThumbnail,
//                                                 @PathVariable Long employmentId) throws IOException {
//        return buildCaseService.updateBuildCase(employmentId, update, employmentThumbnail);
//    }
//
//    @GetMapping
//    public List<BuildcaseResponseDto> selectEmploymentList(Pageable pageable) {
//        return buildCaseService.selectEmploymentList(pageable);
//    }
//
//    @GetMapping("/{buildCaseId}")
//    public BuildcaseResponseDto selectEmployment(@PathVariable Long buildCaseId) {
//        return buildCaseService.selectEmployment(buildCaseId);
//    }
//
//    @DeleteMapping("/{buildCaseId}")
//    public String deleteEmployment(@PathVariable Long buildCaseId) {
//        buildCaseService.deleteEmployment(buildCaseId);
//        return "Delete Completed.";
//    }
}
