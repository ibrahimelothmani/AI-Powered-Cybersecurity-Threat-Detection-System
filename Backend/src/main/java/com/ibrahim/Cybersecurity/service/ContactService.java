package com.ibrahim.Cybersecurity.service;

import com.ibrahim.Cybersecurity.dto.ContactDTO;
import com.ibrahim.Cybersecurity.mapper.ContactMapper;
import com.ibrahim.Cybersecurity.model.Contact;
import com.ibrahim.Cybersecurity.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    public List<ContactDTO> getAllContacts() {
        return contactRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(contactMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<ContactDTO> getContactById(Long id) {
        return contactRepository.findById(id)
                .map(contactMapper::toDTO);
    }

    public ContactDTO createContact(ContactDTO contactDTO) {
        Contact contact = contactMapper.toEntity(contactDTO);
        contact.setCreatedAt(LocalDateTime.now());
        contact.setUpdatedAt(LocalDateTime.now());
        contact.setStatus("PENDING");
        return contactMapper.toDTO(contactRepository.save(contact));
    }

    public Optional<ContactDTO> updateContact(Long id, ContactDTO contactDTO) {
        return contactRepository.findById(id)
                .map(existingContact -> {
                    Contact updatedContact = contactMapper.toEntity(contactDTO);
                    updatedContact.setId(existingContact.getId());
                    updatedContact.setUpdatedAt(LocalDateTime.now());
                    return contactMapper.toDTO(contactRepository.save(updatedContact));
                });
    }

    public Optional<ContactDTO> respondToContact(Long id, String response, String respondedBy) {
        return contactRepository.findById(id)
                .map(contact -> {
                    contact.setResponse(response);
                    contact.setRespondedBy(respondedBy);
                    contact.setRespondedAt(LocalDateTime.now());
                    contact.setStatus("RESPONDED");
                    contact.setUpdatedAt(LocalDateTime.now());
                    return contactMapper.toDTO(contactRepository.save(contact));
                });
    }

    public void deleteContact(Long id) {
        contactRepository.deleteById(id);
    }

    public List<ContactDTO> getPendingContacts() {
        return contactRepository.findByStatus("PENDING").stream()
                .map(contactMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ContactDTO> getContactsByAdminUser(Long adminUserId) {
        return contactRepository.findByAdminUserIdOrderByCreatedAtDesc(adminUserId).stream()
                .map(contactMapper::toDTO)
                .collect(Collectors.toList());
    }
}