package com.doctorshub.doctorshub.services;


import com.doctorshub.doctorshub.models.Role;
import com.doctorshub.doctorshub.models.User;
import com.doctorshub.doctorshub.repositories.RoleRepository;
import com.doctorshub.doctorshub.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Transactional
    public boolean createUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) return false;
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findByRole("ROLE_USER").orElseThrow(() -> new RuntimeException("Role Not Found"));
//        String role = "ROLE_ADMIN";
        user.setRole(role);
        userRepository.save(user);
        return true;
    }
}
