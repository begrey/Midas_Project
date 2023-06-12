package com.midas.midas_project.domain.buildcase;

import com.midas.midas_project.domain.buildcase.dto.BuildcaseRequestDto;
import com.midas.midas_project.domain.buildcase.dto.BuildcaseResponseDto;
import com.midas.midas_project.domain.category.Category;
import com.midas.midas_project.domain.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BuildCaseService {

    private final BuildCaseRepository buildCaseRepository;
    private final CategoryRepository categoryRepository;
    private final BuildCaseFileService buildCaseFileService;
    private final BuildCaseTableRepository buildCaseTableRepository;
    private final BuildCaseBannerRepository buildCaseBannerRepository;

    @Transactional
    public BuildcaseResponseDto saveBuildCase(BuildcaseRequestDto.Post post,
                                              MultipartFile thumbnail,
                                              List<MultipartFile> detail,
                                              List<MultipartFile> banner) throws IOException {

        Category category = categoryRepository.findById(post.getCategoryId()).orElseThrow(() -> new IllegalArgumentException("해당 Category 존재하지 않음"));
        BuildCase buildCase = buildCaseRepository.save(post.toEntity(category));

        //썸네일
        BuildCaseFile thumbnailFile = buildCaseFileService.saveBuildCaseFile(thumbnail, buildCase, "thumbnail");
        buildCase.setBuildCaseFiles(thumbnailFile);
        //상세
        if (!ObjectUtils.isEmpty(detail)) {
            for (MultipartFile file : detail) {
                BuildCaseFile detailFile = buildCaseFileService.saveBuildCaseFile(file, buildCase, "detail");
                buildCase.setBuildCaseFiles(detailFile);
            }
        }
        //테이블
        if (!ObjectUtils.isEmpty(post.getTables())) {
            List<BuildCaseTable> buildCaseTables = new ArrayList<>();
            for(BuildcaseRequestDto.BuildCaseTableDto dto : post.getTables()) {
                buildCaseTables.add(buildCaseTableRepository.save(BuildCaseTable.toEntity(buildCase, dto.getTitle(), dto.getContent())));
            }
            buildCase.setTable(buildCaseTables);
        }
        //배너
        if(!ObjectUtils.isEmpty(banner)) {
            List<BuildCaseBanner> buildCaseBanners = new ArrayList<>();
            int index = 0;
            for (MultipartFile file : banner) {
                // 배너 사진 별도 저장 후 entity save
                BuildCaseFile bannerFile = buildCaseFileService.saveBuildCaseFile(file, buildCase, "banner");
                buildCase.setBuildCaseFiles(bannerFile);
                BuildCaseBanner buildCaseBanner = buildCaseBannerRepository.save(BuildCaseBanner.toEntity(buildCase, post.getBanners().get(index) ,bannerFile));
                buildCaseBanners.add(buildCaseBanner);
                index++;
            }
            buildCase.setBanner(buildCaseBanners);
        }
        return BuildcaseResponseDto.toDto(buildCase);
    }

    @Transactional
    public BuildcaseResponseDto updateBuildCase(BuildcaseRequestDto.Put put, Long BuildCaseId,
                                                MultipartFile thumbnail,
                                                List<MultipartFile> detail,
                                                List<MultipartFile> banner) throws IOException {
        BuildCase updatedBuildCase = buildCaseRepository.findById(BuildCaseId).orElseThrow(() -> new IllegalArgumentException("해당 BuildCase 존재하지 않음"));
        buildCaseFileService.deleteBuildCaseFileById(updatedBuildCase.getBuildCaseId());
        //상세
        if (!ObjectUtils.isEmpty(detail)) {
            for (MultipartFile file : detail) {
                BuildCaseFile detailFile = buildCaseFileService.saveBuildCaseFile(file, updatedBuildCase, "detail");
                updatedBuildCase.setBuildCaseFiles(detailFile);
            }
        }
        //테이블
        if (!ObjectUtils.isEmpty(put.getTables())) {
            List<BuildCaseTable> buildCaseTables = new ArrayList<>();
            for(BuildcaseRequestDto.BuildCaseTableDto dto : put.getTables()) {
                buildCaseTables.add(buildCaseTableRepository.save(BuildCaseTable.toEntity(updatedBuildCase, dto.getTitle(), dto.getContent())));
            }
            updatedBuildCase.setTable(buildCaseTables);
        }
        //배너
        if(!ObjectUtils.isEmpty(banner)) {
            List<BuildCaseBanner> buildCaseBanners = new ArrayList<>();
            int index = 0;
            for (MultipartFile file : banner) {
                // 배너 사진 별도 저장 후 entity save
                BuildCaseFile bannerFile = buildCaseFileService.saveBuildCaseFile(file, updatedBuildCase, "banner");
                updatedBuildCase.setBuildCaseFiles(bannerFile);
                BuildCaseBanner buildCaseBanner = buildCaseBannerRepository.save(BuildCaseBanner.toEntity(updatedBuildCase, put.getBanners().get(index) ,bannerFile));
                buildCaseBanners.add(buildCaseBanner);
                index++;
            }
            updatedBuildCase.setBanner(buildCaseBanners);
        }
        return BuildcaseResponseDto.toDto(updatedBuildCase);
    }

    public List<BuildcaseResponseDto> selectEmploymentList(Pageable pageable){
        Page<BuildCase> buildCases = buildCaseRepository.findAll(pageable);
        return buildCases.stream()
                .map(buildCase -> BuildcaseResponseDto.toDto(buildCase)).toList();
    }

    public BuildcaseResponseDto selectEmployment(Long buildCaseId) {
        BuildCase buildCase = buildCaseRepository.findById(buildCaseId).orElseThrow(() -> new IllegalArgumentException("해당 BuildCase 존재하지 않음"));
        return BuildcaseResponseDto.toDto(buildCase);
    }

    @Transactional
    public void deleteEmployment(Long buildCaseId) {
        buildCaseBannerRepository.deleteByBuildCaseBuildCaseId(buildCaseId);
        buildCaseTableRepository.deleteByBuildCaseBuildCaseId(buildCaseId);
        buildCaseFileService.deleteBuildCaseFileById(buildCaseId);
        buildCaseRepository.deleteById(buildCaseId);
    }
}
