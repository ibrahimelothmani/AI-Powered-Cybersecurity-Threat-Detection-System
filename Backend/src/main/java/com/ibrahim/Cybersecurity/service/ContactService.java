package com.ibrahim.Cybersecurity.service;

import com.ibrahim.Cybersecurity.dto.ContactDTO;
import com.ibrahim.Cybersecurity.mapper.ContactMapper;
import com.ibrahim.Cybersecurity.model.Contact;
import com.ibrahim.Cybersecurity.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContactService {

    private ContactRepository contactRepository;
    private ContactMapper contactMapper;

    public List<ContactDTO> getAllContacts() {
        return contactRepository.findAll().stream()
                .map(contactMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<ContactDTO> getContactById(String id) {
        return contactRepository.findById(Long.valueOf(id))
                .map(contactMapper::toDTO);
    }

    public ContactDTO createContact(ContactDTO contactDTO) {
        Contact contact = contactMapper.toEntity(contactDTO);
        contact.setCreatedAt(LocalDateTime.now());
        contact.setUpdatedAt(LocalDateTime.now());
        contact.setStatus("PENDING");
        return contactMapper.toDTO(contactRepository.save(contact));
    }

    public Optional<ContactDTO> updateContact(String id, ContactDTO contactDTO) {
        return contactRepository.findById(Long.valueOf(id))
                .map(existingContact -> {
                    Contact updatedContact = contactMapper.toEntity(contactDTO);
                    updatedContact.setId(existingContact.getId());
                    updatedContact.setUpdatedAt(LocalDateTime.now());
                    return contactMapper.toDTO(contactRepository.save(updatedContact));
                });
    }

    public Optional<ContactDTO> respondToContact(String id, String response, String respondedBy) {
        return contactRepository.findById(Long.valueOf(id))
                .map(contact -> {
                    contact.setResponse(response);
                    contact.setRespondedBy(respondedBy);
                    contact.setRespondedAt(LocalDateTime.now());
                    contact.setStatus("RESPONDED");
                    contact.setUpdatedAt(LocalDateTime.now());
                    return contactMapper.toDTO(contactRepository.save(contact));
                });
    }

    public void deleteContact(String id) {
        contactRepository.deleteById(Long.valueOf(id));
    }

    public List<ContactDTO> getPendingContacts() {
        return contactRepository.findByStatus("PENDING").stream()
                .map(contactMapper::toDTO)
                .collect(Collectors.toList());
    }
}