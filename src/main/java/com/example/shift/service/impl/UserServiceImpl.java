package com.example.shift.service.impl;

import com.example.shift.dto.UserDto;
import com.example.shift.entity.Role;
import com.example.shift.entity.Users;
import com.example.shift.repository.RoleRepository;
import com.example.shift.repository.UserRepository;
import com.example.shift.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<UserDto> getUserByPhone(String phone) {
        return userRepository.findByPhone(phone)
                .map(this::convertToDto);
    }

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {
        if (userRepository.findByPhone(userDto.getPhone()).isPresent()) {
            throw new IllegalArgumentException("User with this phone number already exists: " + userDto.getPhone());
        }

        Users user = new Users();
        user.setPhone(userDto.getPhone());

        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setFullName(userDto.getFullName());
        user.setEmail(userDto.getEmail());

        if (userDto.getRoles() != null && !userDto.getRoles().isEmpty()) {
            List<Role> roles = userDto.getRoles().stream()
                    .map(roleName -> roleRepository.findByName(roleName)
                            .orElseThrow(() -> new RuntimeException("Role '" + roleName + "' not found!")))
                    .collect(Collectors.toList());
            user.setRoles(roles);
        } else {
            Role defaultUserRole = roleRepository.findByName("USER")
                    .orElseThrow(() -> new RuntimeException("Default 'USER' role not found! Please ensure it's created."));
            user.setRoles(List.of(defaultUserRole));
        }

        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        user = userRepository.save(user);
        return convertToDto(user);
    }

    private UserDto convertToDto(Users user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setPhone(user.getPhone());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());

        List<String> roleNames = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());
        dto.setRoles(roleNames);

        return dto;
    }






}