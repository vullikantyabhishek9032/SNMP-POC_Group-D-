package com.hcl.usermanagement.service;

import com.hcl.usermanagement.dto.RoleRequestDTO;
import com.hcl.usermanagement.entity.RoleResponsibility;
import com.hcl.usermanagement.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public RoleResponsibility createRole(
            RoleRequestDTO dto) {

        RoleResponsibility role =
                new RoleResponsibility();

        role.setName(dto.getName());

        System.out.println(
                "Role Created Successfully");

        return roleRepository.save(role);
    }

    @Override
    public List<RoleResponsibility> getAll() {

        return roleRepository.findAll();
    }

    @Override
    public RoleResponsibility getById(Long id) {

        return roleRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Role Not Found"));
    }
}