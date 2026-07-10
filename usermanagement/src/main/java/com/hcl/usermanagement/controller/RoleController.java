package com.hcl.usermanagement.controller;

import com.hcl.usermanagement.dto.RoleRequestDTO;
import com.hcl.usermanagement.entity.RoleResponsibility;
import com.hcl.usermanagement.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<RoleResponsibility>
    createRole(
            @Valid
            @RequestBody RoleRequestDTO dto){

        return ResponseEntity.ok(
                roleService.createRole(dto));
    }

    @GetMapping
    public List<RoleResponsibility> getAll(){

        return roleService.getAll();
    }

    @GetMapping("/{id}")
    public RoleResponsibility getById(
            @PathVariable Long id){

        return roleService.getById(id);
    }
}