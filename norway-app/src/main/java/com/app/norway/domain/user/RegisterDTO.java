package com.app.norway.domain.user;

public record RegisterDTO(String login, String password, UserRole role) {
}
