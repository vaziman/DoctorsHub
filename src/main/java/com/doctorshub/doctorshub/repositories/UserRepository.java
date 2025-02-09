package com.doctorshub.doctorshub.repositories;


import com.doctorshub.doctorshub.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findUserByUsername(String username);

    @Query(value = "SELECT ur.role_name FROM user_roles ur JOIN users u ON ur.user_id = u.id WHERE u.id = :id", nativeQuery = true)
    String findUserRoleById(@Param("id") Long id);

    String findUserById(Long id);

}
