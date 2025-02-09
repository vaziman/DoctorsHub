package com.doctorshub.doctorshub.services;


import com.doctorshub.doctorshub.models.Role;
import com.doctorshub.doctorshub.models.User;
import com.doctorshub.doctorshub.models.dto.UserForAdminDto;
import com.doctorshub.doctorshub.repositories.RoleRepository;
import com.doctorshub.doctorshub.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    public List<UserForAdminDto> showUsers() {
        List<User> users = userRepository.findAll();
//        return users.stream()
//                .map(user -> new UserForAdminDto(user.getId(), user.getEmail(), user.getRole()))
//                .collect(Collectors.toList());
        return users.stream().map(user -> modelMapper.map(user, UserForAdminDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public UserForAdminDto updateUserRole(Long id, String role) {
        User user = userRepository.findById(id).orElse(null);

        Role newRole = roleRepository.findByRole(role)
                .orElseThrow(() -> new RuntimeException("Role not found " + role));

        user.setRole(newRole);
        User savedUser = userRepository.save(user);

        UserForAdminDto dto = new UserForAdminDto();
        dto.setId(savedUser.getId());
        dto.setEmail(savedUser.getEmail());
        dto.setRole(savedUser.getRole().getRole());
        return dto;
    }
}
