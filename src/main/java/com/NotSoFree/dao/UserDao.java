package com.NotSoFree.dao;

import com.NotSoFree.domain.UserD;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<UserD, Long> {

    UserD findByUsername(String username);
}
