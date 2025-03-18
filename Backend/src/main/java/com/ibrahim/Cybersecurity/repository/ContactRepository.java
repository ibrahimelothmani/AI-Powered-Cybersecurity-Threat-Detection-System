package com.ibrahim.Cybersecurity.repository;

import com.ibrahim.Cybersecurity.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findByUserIdOrderByCreatedAtDesc(Long userId);
    List<Contact> findAllByOrderByCreatedAtDesc();
    List<Contact> findByStatus(String pending);
}