package com.app.norway.services;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

@Service
public class InMemoryTokenBlacklist {
    private Set<String> blacklist = new HashSet<>();

    public void addToBlacklist(String token) {
        blacklist.add(token);
    }
    public boolean isBlacklisted(String token) {
        return blacklist.contains(token);
    }

    public String extractTokenFromRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }
}
