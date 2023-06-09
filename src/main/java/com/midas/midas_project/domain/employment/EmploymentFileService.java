package com.midas.midas_project.domain.employment;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class EmploymentFileService {
    private final EmploymentFileRepository employmentFileRepository;

    @Value("${file.uploadDir}")
    private String filePath;

    public EmploymentFile selectEmploymentFile(Long employmentId) {
        return employmentFileRepository.findByEmploymentEmploymentId(employmentId);
    }

    public EmploymentFile saveEmploymentFile(MultipartFile file, Employment employment) throws IOException {
        String fileFullPath = uploadFile(file);
        return employmentFileRepository.save(EmploymentFile.toEntity(file, fileFullPath, employment));
    }

    public void deleteEmploymentFile(Employment employment) {
        if (ObjectUtils.isEmpty(employment.getEmploymentFile())) return;
        employmentFileRepository.deleteByEmploymentEmploymentId(employment.getEmploymentId());
    }

    public void deleteEmploymentFileById(Long employmentId) {
        employmentFileRepository.deleteByEmploymentEmploymentId(employmentId);
    }

    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        final Path fileUploadPath = Paths.get(filePath).toAbsolutePath().normalize().resolve(fileName);
        Files.copy(file.getInputStream(), fileUploadPath, StandardCopyOption.REPLACE_EXISTING);
        return fileUploadPath.toString();
    }
}
