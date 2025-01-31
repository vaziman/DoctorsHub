package com.doctorshub.doctorshub.controllers;

import com.doctorshub.doctorshub.models.User;
import com.doctorshub.doctorshub.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping
    public String login(@RequestParam String email,
                        @RequestParam String password, Model model, User user) {
        if(!user.getEmail().equals(email) || !user.getPassword().equals(password)) {
            model.addAttribute("error", "Invalid username or password");
        }

        return "redirect:/";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String userRegistration(
                                   @RequestParam String email,
                                   @RequestParam String password,
                                   User user, Model model) {

        user.setEmail(email);
        user.setPassword(password);


        if(!userService.createUser(user)){
            model.addAttribute("errorMessage", "User with email " + email + " already exists!");
            return "registration";
        }
        return "redirect:/login";
    }
}
