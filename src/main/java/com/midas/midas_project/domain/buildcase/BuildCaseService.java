package com.midas.midas_project.domain.buildcase;

import com.midas.midas_project.domain.buildcase.dto.BuildcaseRequestDto;
import com.midas.midas_project.domain.buildcase.dto.BuildcaseResponseDto;
import com.midas.midas_project.domain.category.Category;
import com.midas.midas_project.domain.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BuildCaseService {

    private final BuildCaseRepository buildCaseRepository;
    private final CategoryRepository categoryRepository;
    private final BuildCaseFileService buildCaseFileService;
    private final ModelMapper modelMapper;

    @Transactional
    public BuildcaseResponseDto saveBuildCase(BuildcaseRequestDto.Post post,
                                              MultipartFile thumbnail,
                                              List<MultipartFile> detail,
                                              List<MultipartFile> banner) throws IOException {

        Category category = categoryRepository.findById(post.getCategoryId()).orElseThrow(() -> new IllegalArgumentException("해당 Category 존재하지 않음"));
        BuildCase buildCase = buildCaseRepository.save(post.toEntity(category));
        //썸네일
        BuildCaseFile thumbnailFile = buildCaseFileService.saveBuildCaseFile(thumbnail, buildCase, "thumbnail");
        buildCase.setBuildCaseFilesFile(thumbnailFile);
        //상세
        if (!ObjectUtils.isEmpty(detail)) {
            for (MultipartFile file : detail) {
                BuildCaseFile detailFile = buildCaseFileService.saveBuildCaseFile(file, buildCase, "detail");
                buildCase.setBuildCaseFilesFile(detailFile);
            }
        }
        //테이블

        //배너
        return null;
    }

    @Transactional
    public BuildcaseResponseDto updateBuildCase(BuildcaseRequestDto.Put put, Long BuildCaseId) {
        return null;
    }
}
