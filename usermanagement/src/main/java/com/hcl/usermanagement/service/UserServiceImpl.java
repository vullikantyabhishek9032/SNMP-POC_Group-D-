package com.hcl.usermanagement.service;

import com.hcl.usermanagement.dto.UserRequestDTO;
import com.hcl.usermanagement.entity.RoleResponsibility;
import com.hcl.usermanagement.entity.User;
import com.hcl.usermanagement.repository.RoleRepository;
import com.hcl.usermanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public User createUser(UserRequestDTO dto) {

        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {

            System.out.println("Email Already Registered");

            throw new RuntimeException(
                    "Email Already Registered");
        }

        if (userRepository.findByUsername(dto.getUsername())
                .isPresent()) {

            System.out.println("Username Already Exists");

            throw new RuntimeException(
                    "Username Already Exists");
        }

        RoleResponsibility role =
                roleRepository.findById(dto.getRoleId())
                        .orElse(null);

        if (role == null) {

            System.out.println(
                    "Role Not Found For Id : "
                            + dto.getRoleId());

            throw new RuntimeException(
                    "Role Not Found For Id : "
                            + dto.getRoleId());
        }

        User user = new User();

        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setMobileNumber(dto.getMobileNumber());
        user.setRoleId(dto.getRoleId());

        System.out.println(
                "User Registered Successfully");

        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {

        System.out.println("Fetching All Users");

        return userRepository.findAll();
    }

    @Override
    public User getUser(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() -> {

                    System.out.println(
                            "User Not Found For Id : " + id);

                    return new RuntimeException(
                            "User Not Found");
                });
    }

    @Override
    public User updateUser(Long id,
                           UserRequestDTO dto) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> {

                    System.out.println(
                            "User Not Found For Id : " + id);

                    return new RuntimeException(
                            "User Not Found");
                });

        RoleResponsibility role =
                roleRepository.findById(dto.getRoleId())
                        .orElse(null);

        if (role == null) {

            System.out.println(
                    "Role Not Found For Id : "
                            + dto.getRoleId());

            throw new RuntimeException(
                    "Role Not Found");
        }

        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setMobileNumber(dto.getMobileNumber());
        user.setRoleId(dto.getRoleId());

        System.out.println(
                "User Updated Successfully");

        return userRepository.save(user);
    }

    @Override
    public User patchUser(Long id,
                          UserRequestDTO dto) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> {

                    System.out.println(
                            "User Not Found For Id : " + id);

                    return new RuntimeException(
                            "User Not Found");
                });

        if (dto.getUsername() != null) {
            user.setUsername(dto.getUsername());
        }

        if (dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
        }

        if (dto.getPassword() != null) {
            user.setPassword(dto.getPassword());
        }

        if (dto.getMobileNumber() != null) {
            user.setMobileNumber(dto.getMobileNumber());
        }

        if (dto.getRoleId() != null) {

            RoleResponsibility role =
                    roleRepository.findById(dto.getRoleId())
                            .orElse(null);

            if (role == null) {

                System.out.println(
                        "Role Not Found For Id : "
                                + dto.getRoleId());

                throw new RuntimeException(
                        "Role Not Found");
            }

            user.setRoleId(dto.getRoleId());
        }

        System.out.println(
                "User Partially Updated Successfully");

        return userRepository.save(user);
    }

    @Override
    public String deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> {

                    System.out.println(
                            "User Not Found For Id : " + id);

                    return new RuntimeException(
                            "User Not Found");
                });

        userRepository.delete(user);

        System.out.println(
                "User Deleted Successfully");

        return "User Deleted Successfully";
    }
}