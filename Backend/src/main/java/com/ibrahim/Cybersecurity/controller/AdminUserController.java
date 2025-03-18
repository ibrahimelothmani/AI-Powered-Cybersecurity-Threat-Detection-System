package com.ibrahim.Cybersecurity.controller;

import com.ibrahim.Cybersecurity.dto.AdminUserDTO;
import com.ibrahim.Cybersecurity.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {

    private AdminUserService adminUserService;

    @GetMapping("/users")
    public ResponseEntity<List<AdminUserDTO>> getAllAdminUsers() {
        return ResponseEntity.ok(adminUserService.getAllAdminUsers());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<AdminUserDTO> getAdminUserById(@PathVariable String id) {
        return adminUserService.getAdminUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/users")
    public ResponseEntity<AdminUserDTO> createAdminUser(@RequestBody AdminUserDTO adminUserDTO) {
        return ResponseEntity.ok(adminUserService.createAdminUser(adminUserDTO));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<AdminUserDTO> updateAdminUser(@PathVariable String id, @RequestBody AdminUserDTO adminUserDTO) {
        return adminUserService.updateAdminUser(id, adminUserDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteAdminUser(@PathVariable String id) {
        adminUserService.deleteAdminUser(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/users/{id}/access-level")
    public ResponseEntity<AdminUserDTO> updateAccessLevel(@PathVariable String id, @RequestBody String accessLevel) {
        return adminUserService.updateAccessLevel(id, accessLevel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/users/{id}/login")
    public ResponseEntity<AdminUserDTO> updateLastLogin(@PathVariable String id) {
        return adminUserService.updateLastLogin(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}