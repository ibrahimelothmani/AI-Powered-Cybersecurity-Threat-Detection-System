package com.ibrahim.Cybersecurity.repository;

import com.ibrahim.Cybersecurity.model.AdminUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminUserRepository extends MongoRepository<AdminUser, String> {
    Optional<AdminUser> findByEmail(String email);
    boolean existsByEmail(String email);
}