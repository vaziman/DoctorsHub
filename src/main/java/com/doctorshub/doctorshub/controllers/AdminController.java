package com.doctorshub.doctorshub.controllers;

import com.doctorshub.doctorshub.models.dto.UpdateUserRoleDto;
import com.doctorshub.doctorshub.models.dto.UserForAdminDto;
import com.doctorshub.doctorshub.services.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@SecurityRequirement(name = "SessionAuth")
@Tag(name = "Admin API", description = "Settings for Admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @Operation(summary = "Admin Panel", description = "Endpoint to open an admin panel")
    @GetMapping("/panel")
    public ResponseEntity<Map<String, String>> adminPanel() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Admin Panel");
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Show Users", description = "Endpoint to show table of users")
    @GetMapping("/users")
    public ResponseEntity<List<UserForAdminDto>> adminUsers() {
        List<UserForAdminDto> users = adminService.showUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/users/{id}/role")
    public ResponseEntity<UserForAdminDto> updateUserRole(@PathVariable("id") Long id,
                                                          @RequestBody UpdateUserRoleDto updateUserRoleDto
                                                          ){

        UserForAdminDto updateUser = adminService.updateUserRole(id, updateUserRoleDto.getRole());
        return ResponseEntity.ok(updateUser);
    }
}
