package com.midas.midas_project.domain.buildcase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildCaseBannerRepository extends JpaRepository<BuildCaseBanner, Long> {
    void deleteByBuildCaseBuildCaseId(Long buildCaseId);
}
