package com.midas.midas_project.domain.employment;

import com.midas.midas_project.domain.employment.dto.EmploymentRequestDto;
import com.midas.midas_project.domain.employment.dto.EmploymentResponseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmploymentService {

    private final EmploymentRepository employmentRepository;
    private final EmploymentFileService employmentFileService;
    private final ModelMapper modelMapper;


    @Transactional
    public EmploymentResponseDto saveEmployment(EmploymentRequestDto.Post post, MultipartFile employmentThumbnail) throws IOException {
        Employment employmentEntity = employmentRepository.save(post.toEntity());
        EmploymentResponseDto dto = EmploymentResponseDto.toDto(employmentEntity, modelMapper);
        // File save
        if (!ObjectUtils.isEmpty(employmentThumbnail)) {
            EmploymentFile employmentFile = employmentFileService.saveEmploymentFile(employmentThumbnail, employmentEntity);
            employmentEntity.setEmploymentFiles(employmentFile);
            dto.setFilePath(employmentFile.getFilePath());
        }
        return dto;
    }

    @Transactional
    public EmploymentResponseDto updateEmployment(Long employmentId, EmploymentRequestDto.Put update, MultipartFile employmentThumbnail) throws IOException {
        Employment employment = employmentRepository.findById(employmentId).orElseThrow(() -> new IllegalArgumentException("해당 Employment 존재하지 않음"));
        Employment updateEmployment = employment.update(update);
        EmploymentResponseDto dto = EmploymentResponseDto.toDto(updateEmployment, modelMapper);

        if (!ObjectUtils.isEmpty(employmentThumbnail)) {
            employmentFileService.deleteEmploymentFile(updateEmployment);
            updateEmployment.setEmploymentFiles(employmentFileService.saveEmploymentFile(employmentThumbnail, updateEmployment));
            dto.setFilePath(updateEmployment.getEmploymentFile().getFilePath());
        }
        return dto;
    }

    public EmploymentResponseDto selectEmployment(Long employmentId) {
        Employment employment = employmentRepository.findById(employmentId).orElseThrow(() -> new IllegalArgumentException("해당 Employment 존재하지 않음"));

        EmploymentResponseDto dto = EmploymentResponseDto.toDto(employment, modelMapper);
        return dto;
    }

    public List<EmploymentResponseDto> selectEmploymentList(Pageable pageable) {
        Page<Employment> employments = employmentRepository.findAll(pageable);
        return employments.stream()
                .map(employment -> EmploymentResponseDto.toDto(employment, modelMapper)).toList();
    }

    @Transactional
    public void deleteEmployment(Long employmentId) {
        employmentFileService.deleteEmploymentFileById(employmentId);
        employmentRepository.deleteById(employmentId);
    }
}
