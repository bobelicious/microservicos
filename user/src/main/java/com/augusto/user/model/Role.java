package com.augusto.user.model;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public final class Role {
    private Long id;
    private String role;

    public Long getId() {
        return id;
    }

    public String getRole() {
        return role;
    }
}
