package com.midas.midas_project.domain.employment;

import com.midas.midas_project.domain.employment.dto.EmploymentRequestDto;
import com.midas.midas_project.domain.employment.dto.EmploymentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping(value = "/employments")
@RestController
public class EmploymentController {
    private final EmploymentService employmentService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public EmploymentResponseDto createEmployment(@RequestPart EmploymentRequestDto.Post post,
                                                  @RequestPart(required = false, value = "file") MultipartFile employmentThumbnail) throws IOException {
        return employmentService.saveEmployment(post, employmentThumbnail);
    }

    @PutMapping(value = "/{employmentId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public EmploymentResponseDto updateEmployment(@RequestPart EmploymentRequestDto.Put update,
                                                  @RequestPart(required = false, value = "file") MultipartFile employmentThumbnail,
                                                  @PathVariable Long employmentId) throws IOException {
        return employmentService.updateEmployment(employmentId, update, employmentThumbnail);
    }

    @GetMapping
    public List<EmploymentResponseDto> selectEmploymentList(Pageable pageable) {
        return employmentService.selectEmploymentList(pageable);
    }

    @GetMapping("/{employmentId}")
    public EmploymentResponseDto selectEmployment(@PathVariable Long employmentId) {
        return employmentService.selectEmployment(employmentId);
    }

    @DeleteMapping("/{employmentId}")
    public String deleteEmployment(@PathVariable Long employmentId) {
        employmentService.deleteEmployment(employmentId);
        return "Delete Completed.";
    }
}
