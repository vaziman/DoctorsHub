package com.doctorshub.doctorshub.services;



import com.doctorshub.doctorshub.models.User;
import com.doctorshub.doctorshub.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.doctorshub.doctorshub.enums.UserRole;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public boolean createUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) return false;
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        user.getRoles().add(UserRole.USER);
        user.setRole(UserRole.ROLE_USER.toString());
        userRepository.save(user);
        return true;
    }
}
