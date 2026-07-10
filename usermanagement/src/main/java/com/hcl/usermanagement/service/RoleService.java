package com.hcl.usermanagement.service;

import com.hcl.usermanagement.dto.RoleRequestDTO;
import com.hcl.usermanagement.entity.RoleResponsibility;

import java.util.List;

public interface RoleService {

    RoleResponsibility createRole(
            RoleRequestDTO dto);

    List<RoleResponsibility> getAll();

    RoleResponsibility getById(Long id);
}