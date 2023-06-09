package com.midas.midas_project.domain.employment.dto;

import com.midas.midas_project.domain.employment.Employment;
import com.midas.midas_project.domain.employment.EmploymentFile;
import com.midas.midas_project.domain.user.dto.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.modelmapper.ModelMapper;
import org.springframework.util.ObjectUtils;

import javax.persistence.Column;
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
