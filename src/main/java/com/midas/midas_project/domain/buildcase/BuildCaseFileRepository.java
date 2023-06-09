package com.midas.midas_project.domain.buildcase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildCaseFileRepository extends JpaRepository<BuildCaseFile, Long> {
    BuildCaseFile findByBuildCaseBuildCaseId(Long buildCaseId);
    void deleteByBuildCaseBuildCaseId(Long buildCaseId);
}
