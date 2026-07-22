package com.hcl.usermanagement.controller;

import com.hcl.usermanagement.dto.UserRequestDTO;
import com.hcl.usermanagement.entity.User;
import com.hcl.usermanagement.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins ="http://localhost:3000")
public class UserController {

    private final UserService userService;

    // Create User
    @PostMapping("/create")
    public ResponseEntity<?> createUser(
            @Valid
            @RequestBody UserRequestDTO dto) {

        return ResponseEntity.ok(
                userService.createUser(dto));
    }

    // Get All Users
    @GetMapping
    public List<User> getAllUsers() {

        return userService.getAllUsers();
    }

    // Get User By Id
    @GetMapping("/{id}")
    public User getById(
            @PathVariable Long id) {

        return userService.getUser(id);
    }

    // Update Complete User
    @PutMapping("/{id}")
    public User updateUser(
            @PathVariable Long id,
            @Valid
            @RequestBody UserRequestDTO dto) {

        return userService.updateUser(id, dto);
    }

    // Partial Update User
    @PatchMapping("/{id}")
    public User patchUser(
            @PathVariable Long id,
            @RequestBody UserRequestDTO dto) {

        return userService.patchUser(id, dto);
    }

    // Delete User
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                userService.deleteUser(id));
    }
}