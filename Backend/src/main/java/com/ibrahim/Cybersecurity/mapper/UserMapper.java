package com.ibrahim.Cybersecurity.mapper;

import com.ibrahim.Cybersecurity.dto.UserDTO;
import com.ibrahim.Cybersecurity.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(User user);
    User toEntity(UserDTO userDTO);
}