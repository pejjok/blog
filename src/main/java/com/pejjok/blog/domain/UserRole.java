package com.pejjok.blog.domain;

public enum UserRole {
    USER("ROLE_USER"),
    EDITOR("ROLE_EDITOR"),
    ADMIN("ROLE_ADMIN");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public String withoutPrefix() {
        return name();
    }
}