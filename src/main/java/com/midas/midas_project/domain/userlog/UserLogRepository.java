package com.midas.midas_project.domain.userlog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLogRepository extends JpaRepository<UserLog, Long> {
    Page<UserLog> findByMidasUserId(String keyword, Pageable pageable);
}
