package com.midas.midas_project.domain.employment.dto;

import com.midas.midas_project.domain.employment.Employment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmploymentResponseDto {
    private Long employmentId;
    private String position;
    private String occupation;
    private String assignedTask;
    private String qualification;
    private String isVisible;
    private LocalDateTime recruitStartDatetime;
    private LocalDateTime recruitEndDatetime;
    private String filePath;

    public static EmploymentResponseDto toDto(Employment employment, ModelMapper modelMapper) {
        EmploymentResponseDto dto = modelMapper.map(employment, EmploymentResponseDto.class);
        if (!ObjectUtils.isEmpty(employment.getEmploymentFile()))
            dto.setFilePath(employment.getEmploymentFile().getFilePath());
        return dto;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
