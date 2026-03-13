package com.thanhluanmobile.repository;

import com.thanhluanmobile.entity.ERole;
import com.thanhluanmobile.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
}