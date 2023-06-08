package com.midas.midas_project.domain.employment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmploymentFileRepository extends JpaRepository<EmploymentFile, Long> {
}
