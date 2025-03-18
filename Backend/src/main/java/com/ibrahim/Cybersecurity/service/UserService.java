package com.ibrahim.Cybersecurity.service;

import com.ibrahim.Cybersecurity.dto.UserDTO;
import com.ibrahim.Cybersecurity.mapper.UserMapper;
import com.ibrahim.Cybersecurity.model.User;
import com.ibrahim.Cybersecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<UserDTO> getUserById(String id) {
        return userRepository.findById(Long.valueOf(id))
                .map(userMapper::toDTO);
    }

    public UserDTO createUser(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setEnabled(true);
        user.setRole("USER");
        return userMapper.toDTO(userRepository.save(user));
    }

    public Optional<UserDTO> updateUser(String id, UserDTO userDTO) {
        return userRepository.findById(Long.valueOf(id))
                .map(existingUser -> {
                    User updatedUser = userMapper.toEntity(userDTO);
                    updatedUser.setId(existingUser.getId());
                    updatedUser.setUpdatedAt(LocalDateTime.now());
                    return userMapper.toDTO(userRepository.save(updatedUser));
                });
    }

    public void deleteUser(String id) {
        userRepository.deleteById(Long.valueOf(id));
    }

    public Optional<UserDTO> updateLastLogin(String id) {
        return userRepository.findById(Long.valueOf(id))
                .map(user -> {
                    user.setLastLogin(LocalDateTime.now());
                    return userMapper.toDTO(userRepository.save(user));
                });
    }
}