package com.ibrahim.Cybersecurity.mapper;

import com.ibrahim.Cybersecurity.dto.ContactDTO;
import com.ibrahim.Cybersecurity.model.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContactMapper {
    ContactDTO toDTO(Contact contact);
    Contact toEntity(ContactDTO contactDTO);
}