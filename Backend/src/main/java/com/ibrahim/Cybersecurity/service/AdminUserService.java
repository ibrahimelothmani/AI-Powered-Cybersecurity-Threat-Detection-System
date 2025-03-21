package com.ibrahim.Cybersecurity.service;

import com.ibrahim.Cybersecurity.dto.AdminUserDTO;
import com.ibrahim.Cybersecurity.mapper.AdminUserMapper;
import com.ibrahim.Cybersecurity.model.AdminUser;
import com.ibrahim.Cybersecurity.repository.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final AdminUserRepository adminUserRepository;
    private final AdminUserMapper adminUserMapper;
    private final PasswordEncoder passwordEncoder;

    public List<AdminUserDTO> getAllAdminUsers() {
        return adminUserRepository.findAll().stream()
                .map(adminUserMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<AdminUserDTO> getAdminUserById(Long id) {
        return adminUserRepository.findById(id)
                .map(adminUserMapper::toDTO);
    }

    public AdminUserDTO createAdminUser(AdminUserDTO adminUserDTO) {
        AdminUser adminUser = adminUserMapper.toEntity(adminUserDTO);
        adminUser.setPassword(passwordEncoder.encode(adminUser.getPassword()));
        adminUser.setCreatedAt(LocalDateTime.now());
        adminUser.setUpdatedAt(LocalDateTime.now());
        adminUser.setEnabled(true);
        return adminUserMapper.toDTO(adminUserRepository.save(adminUser));
    }

    public Optional<AdminUserDTO> updateAdminUser(Long id, AdminUserDTO adminUserDTO) {
        return adminUserRepository.findById(id)
                .map(existingUser -> {
                    AdminUser updatedUser = adminUserMapper.toEntity(adminUserDTO);
                    updatedUser.setId(existingUser.getId());
                    updatedUser.setUpdatedAt(LocalDateTime.now());
                    if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                        updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                    } else {
                        updatedUser.setPassword(existingUser.getPassword());
                    }
                    return adminUserMapper.toDTO(adminUserRepository.save(updatedUser));
                });
    }

    public void deleteAdminUser(Long id) {
        adminUserRepository.deleteById(id);
    }

    public Optional<AdminUserDTO> findByUsername(String username) {
        return adminUserRepository.findByUsername(username)
                .map(adminUserMapper::toDTO);
    }

    public Optional<AdminUserDTO> findByEmail(String email) {
        return adminUserRepository.findByEmail(email)
                .map(adminUserMapper::toDTO);
    }

    public Optional<AdminUserDTO> updateLastLogin(Long id) {
        return adminUserRepository.findById(id)
                .map(adminUser -> {
                    adminUser.setLastLogin(LocalDateTime.now());
                    adminUser.setUpdatedAt(LocalDateTime.now());
                    return adminUserMapper.toDTO(adminUserRepository.save(adminUser));
                });
    }

    public Optional<AdminUserDTO> updateAccessLevel(Long id, String accessLevel) {
        return adminUserRepository.findById(id)
                .map(adminUser -> {
                    adminUser.setAccessLevel(accessLevel);
                    adminUser.setUpdatedAt(LocalDateTime.now());
                    return adminUserMapper.toDTO(adminUserRepository.save(adminUser));
                });
    }
}