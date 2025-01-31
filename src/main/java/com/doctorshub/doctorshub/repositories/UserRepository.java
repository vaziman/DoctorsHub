package com.doctorshub.doctorshub.repositories;


import com.doctorshub.doctorshub.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findUserByUsername(String username);
}
