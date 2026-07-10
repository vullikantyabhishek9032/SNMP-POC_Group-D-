package com.hcl.usermanagement.service;

import com.hcl.usermanagement.dto.UserRequestDTO;
import com.hcl.usermanagement.entity.User;

import java.util.List;
public interface UserService {

    User createUser(UserRequestDTO dto);

    List<User> getAllUsers();

    User getUser(Long id);

    User updateUser(
            Long id,
            UserRequestDTO dto);

    User patchUser(
            Long id,
            UserRequestDTO dto);

    String deleteUser(Long id);
}