package com.hcl.usermanagement.dto;

import jakarta.validation.constraints.NotBlank;

public class RoleRequestDTO {

    @NotBlank(message = "Role Name Required")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}