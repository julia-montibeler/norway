package com.app.norway.domain.user;

public enum UserRole {

    ADMIN("admin"),

    USER("user");

    private String role;

    UserRole(String role){
        this.role = role;
    }
}