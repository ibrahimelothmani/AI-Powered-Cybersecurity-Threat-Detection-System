package com.ibrahim.Cybersecurity.controller;

import com.ibrahim.Cybersecurity.dto.AdminUserDTO;
import com.ibrahim.Cybersecurity.service.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin-users")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4201")
public class AdminUserController {

    private final AdminUserService adminUserService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AdminUserDTO>> getAllAdminUsers() {
        return ResponseEntity.ok(adminUserService.getAllAdminUsers());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminUserDTO> getAdminUserById(@PathVariable Long id) {
        return adminUserService.getAdminUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminUserDTO> createAdminUser(@RequestBody AdminUserDTO adminUserDTO) {
        return ResponseEntity.ok(adminUserService.createAdminUser(adminUserDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminUserDTO> updateAdminUser(@PathVariable Long id, @RequestBody AdminUserDTO adminUserDTO) {
        return adminUserService.updateAdminUser(id, adminUserDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAdminUser(@PathVariable Long id) {
        adminUserService.deleteAdminUser(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/access-level")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminUserDTO> updateAccessLevel(@PathVariable Long id, @RequestParam String accessLevel) {
        return adminUserService.updateAccessLevel(id, accessLevel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/last-login")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminUserDTO> updateLastLogin(@PathVariable Long id) {
        return adminUserService.updateLastLogin(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-username/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminUserDTO> findByUsername(@PathVariable String username) {
        return adminUserService.findByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-email/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminUserDTO> findByEmail(@PathVariable String email) {
        return adminUserService.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}