package com.doctorshub.doctorshub.models.dto;


import com.doctorshub.doctorshub.enums.UserRole;
import lombok.Data;

import java.util.Set;

@Data
public class UserForAdminDto {

    private Long id;
    private String email;
    private String role;

    public UserForAdminDto() {

    }
}

