package com.hcl.usermanagement.repository;


import com.hcl.usermanagement.entity.RoleResponsibility;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository
        extends JpaRepository<RoleResponsibility,Long> {
}