package com.ibrahim.Cybersecurity.mapper;

import com.ibrahim.Cybersecurity.dto.AdminUserDTO;
import com.ibrahim.Cybersecurity.model.AdminUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdminUserMapper {
    AdminUserDTO toDTO(AdminUser adminUser);
    AdminUser toEntity(AdminUserDTO adminUserDTO);
}