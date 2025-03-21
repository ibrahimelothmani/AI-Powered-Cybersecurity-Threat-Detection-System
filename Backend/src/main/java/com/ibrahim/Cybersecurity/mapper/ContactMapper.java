package com.ibrahim.Cybersecurity.mapper;

import com.ibrahim.Cybersecurity.dto.ContactDTO;
import com.ibrahim.Cybersecurity.model.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContactMapper {
    @Mapping(source = "adminUser.id", target = "adminUserId")
    ContactDTO toDTO(Contact contact);

    @Mapping(target = "adminUser", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Contact toEntity(ContactDTO contactDTO);
}