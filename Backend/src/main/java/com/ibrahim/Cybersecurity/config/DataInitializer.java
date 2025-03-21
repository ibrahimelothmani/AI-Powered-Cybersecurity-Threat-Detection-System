package com.ibrahim.Cybersecurity.config;

import com.ibrahim.Cybersecurity.model.AdminUser;
import com.ibrahim.Cybersecurity.repository.AdminUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private AdminUserRepository adminUserRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Create default admin user if not exists
        if (adminUserRepository.count() == 0) {
            AdminUser adminUser = new AdminUser();
            adminUser.setUsername("admin");
            adminUser.setPassword(passwordEncoder.encode("admin123"));
            adminUser.setEmail("admin@cybersecurity.com");
            adminUser.setRole("ROLE_ADMIN");
            adminUserRepository.save(adminUser);
        }
    }
}
