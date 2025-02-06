package com.doctorshub.doctorshub.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController

@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {

    @GetMapping("/panel")
    public ResponseEntity<Map<String, String>> adminPanel() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Admin Panel");
        return ResponseEntity.ok(response);
    }
}
