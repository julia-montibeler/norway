package com.app.norway.infra.security;

import com.app.norway.services.InMemoryTokenBlacklist;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtTokenFilter extends OncePerRequestFilter {
    @Autowired
    private InMemoryTokenBlacklist inMemoryTokenBlacklist = new InMemoryTokenBlacklist();

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = inMemoryTokenBlacklist.extractTokenFromRequest(request);

        if (token != null && !inMemoryTokenBlacklist.isBlacklisted(token)) {
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
