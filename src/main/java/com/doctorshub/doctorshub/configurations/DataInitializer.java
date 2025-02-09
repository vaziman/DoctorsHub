package com.doctorshub.doctorshub.configurations;


import com.doctorshub.doctorshub.models.Role;
import com.doctorshub.doctorshub.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.findByRole("ROLE_ADMIN").isEmpty()) {
            Role admin = new Role();
            admin.setRole("ROLE_ADMIN");
            roleRepository.save(admin);
        }

        if (roleRepository.findByRole("ROLE_USER").isEmpty()) {
            Role user = new Role();
            user.setRole("ROLE_USER");
            roleRepository.save(user);
        }
        if(roleRepository.findByRole("ROLE_DOCTOR").isEmpty()) {
            Role doctor = new Role();
            doctor.setRole("ROLE_DOCTOR");
            roleRepository.save(doctor);
        }
    }
}
