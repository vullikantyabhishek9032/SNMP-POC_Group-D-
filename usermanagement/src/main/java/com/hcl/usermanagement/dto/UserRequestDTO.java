package com.hcl.usermanagement.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserRequestDTO {

    @NotBlank(message = "Username required")
    private String username;

    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "Password required")
    private String password;

    @Pattern(
            regexp="^[0-9]{10}$",
            message="Mobile must be 10 digits"
    )
    private String mobileNumber;

    private Long roleId;
}