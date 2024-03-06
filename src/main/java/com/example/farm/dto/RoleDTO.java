package com.example.farm.dto;

import org.springframework.security.core.GrantedAuthority;

public class RoleDTO implements GrantedAuthority {
    private Integer roleId;
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }
}
