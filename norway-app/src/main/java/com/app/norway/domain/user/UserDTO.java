package com.app.norway.domain.user;

import java.util.UUID;

public record UserDTO(UUID Id, String login, UserRole role) {
}
