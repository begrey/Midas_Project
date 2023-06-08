package com.midas.midas_project.domain.user;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByMidasUserId(String midasUserId);
    User findByMidasUserIdAndPassword(String midasUserId, String password);

}
