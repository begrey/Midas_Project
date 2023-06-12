package com.midas.midas_project.domain.buildcase;

import com.midas.midas_project.domain.employment.Employment;
import com.midas.midas_project.domain.employment.EmploymentFile;
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
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BuildCaseFileService {
    private final BuildCaseFileRepository buildCaseFileRepository;

    @Value("${file.uploadDir}")
    private String filePath;

    public BuildCaseFile selectBuildCaseFile(Long buildCaseId) {
        return buildCaseFileRepository.findByBuildCaseBuildCaseId(buildCaseId);
    }


    public BuildCaseFile saveBuildCaseFile(MultipartFile file, BuildCase buildCase, String type) throws IOException {
        String fileFullPath = uploadFile(file);
        return buildCaseFileRepository.save(BuildCaseFile.toEntity(file, fileFullPath, buildCase, type));
    }

    public void deleteBuildCaseFile(BuildCase buildCase) {
        if (ObjectUtils.isEmpty(buildCase.getBuildCaseFiles())) return;
        buildCaseFileRepository.deleteByBuildCaseBuildCaseId(buildCase.getBuildCaseId());
    }

    public void deleteBuildCaseFileById(Long buldCaseId) {
        buildCaseFileRepository.deleteByBuildCaseBuildCaseId(buldCaseId);
    }

    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        final Path fileUploadPath = Paths.get(filePath).toAbsolutePath().normalize().resolve(fileName);
        Files.copy(file.getInputStream(), fileUploadPath, StandardCopyOption.REPLACE_EXISTING);
        return fileUploadPath.toString();
    }
}
