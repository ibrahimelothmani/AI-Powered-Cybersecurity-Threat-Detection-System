package com.ibrahim.Cybersecurity.service;

import com.ibrahim.Cybersecurity.dto.AdminUserDTO;
import com.ibrahim.Cybersecurity.mapper.AdminUserMapper;
import com.ibrahim.Cybersecurity.model.AdminUser;
import com.ibrahim.Cybersecurity.repository.AdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminUserService {

    private AdminUserRepository adminUserRepository;
    private AdminUserMapper adminUserMapper;
    private PasswordEncoder passwordEncoder;

    public List<AdminUserDTO> getAllAdminUsers() {
        return adminUserRepository.findAll().stream()
                .map(adminUserMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<AdminUserDTO> getAdminUserById(String id) {
        return adminUserRepository.findById(id)
                .map(adminUserMapper::toDTO);
    }

    public AdminUserDTO createAdminUser(AdminUserDTO adminUserDTO) {
        AdminUser adminUser = adminUserMapper.toEntity(adminUserDTO);
        adminUser.setCreatedAt(LocalDateTime.now());
        adminUser.setUpdatedAt(LocalDateTime.now());
        adminUser.setEnabled(true);
        adminUser.setRole("ADMIN");
        return adminUserMapper.toDTO(adminUserRepository.save(adminUser));
    }

    public Optional<AdminUserDTO> updateAdminUser(String id, AdminUserDTO adminUserDTO) {
        return adminUserRepository.findById(id)
                .map(existingUser -> {
                    AdminUser updatedUser = adminUserMapper.toEntity(adminUserDTO);
                    updatedUser.setId(existingUser.getId());
                    updatedUser.setUpdatedAt(LocalDateTime.now());
                    return adminUserMapper.toDTO(adminUserRepository.save(updatedUser));
                });
    }

    public void deleteAdminUser(String id) {
        adminUserRepository.deleteById(id);
    }

    public Optional<AdminUserDTO> updateLastLogin(String id) {
        return adminUserRepository.findById(id)
                .map(adminUser -> {
                    adminUser.setLastLogin(LocalDateTime.now());
                    return adminUserMapper.toDTO(adminUserRepository.save(adminUser));
                });
    }

    public Optional<AdminUserDTO> updateAccessLevel(String id, String accessLevel) {
        return adminUserRepository.findById(id)
                .map(adminUser -> {
                    adminUser.setAccessLevel(accessLevel);
                    adminUser.setUpdatedAt(LocalDateTime.now());
                    return adminUserMapper.toDTO(adminUserRepository.save(adminUser));
                });
    }
}