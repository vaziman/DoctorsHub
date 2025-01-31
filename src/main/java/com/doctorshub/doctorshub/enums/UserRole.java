package com.doctorshub.doctorshub.enums;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    ROLE_USER, ROLE_DOCTOR, ROLE_ADMIN;


    @Override
    public String getAuthority() {
        return name();
    }
}
