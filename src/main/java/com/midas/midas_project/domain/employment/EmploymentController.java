package com.midas.midas_project.domain.employment;

import com.midas.midas_project.domain.employment.dto.EmploymentRequestDto;
import com.midas.midas_project.domain.employment.dto.EmploymentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping(value = "/employments")
@RestController
public class EmploymentController {
    private final EmploymentService employmentService;
    private final EmploymentFileService employmentFileService;

    @PostMapping
    public EmploymentResponseDto createEmployment(@RequestBody EmploymentRequestDto.Post post) {
        return employmentService.saveEmployment(post);
    }
}
