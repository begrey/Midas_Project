package com.midas.midas_project.domain.employment;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class EmploymentFileService {
    private final EmploymentFileRepository employmentFileRepository;

    @Value("${file.uploadDir}")
    private String filePath;

    public EmploymentFile saveEmploymentFile(MultipartFile file) {
        return employmentFileRepository.save(EmploymentFile.toEntity(file, filePath));
    }
}
