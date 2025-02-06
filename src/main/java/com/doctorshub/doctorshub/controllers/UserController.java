package com.doctorshub.doctorshub.controllers;

import com.doctorshub.doctorshub.models.User;
import com.doctorshub.doctorshub.repositories.UserRepository;
import com.doctorshub.doctorshub.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "User API", description = "Operations related to user registration and login")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    @Operation(summary = "User login", description = "Endpoint for logging in a user")
    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email,
                                   @RequestParam String password) {

        User existingUser = userRepository.findByEmail(email);
        if (existingUser == null || !existingUser.getPassword().equals(password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }
        return ResponseEntity.ok().body(existingUser);
    }


    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @Operation(summary = "User registration", description = "Endpoint for registring a new user")
    @PostMapping("/registration")
    public ResponseEntity<?> userRegistration(
                                   @RequestParam String email,
                                   @RequestParam String password
                                   ) {

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        if(!userService.createUser(user)){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("User with email " + email + " already exists!");

        }
        return ResponseEntity.ok("User registered successfully");
    }
}
