package com.app.norway.controllers;

import com.app.norway.domain.user.AuthDTO;
import com.app.norway.domain.user.LoginResponseDTO;
import com.app.norway.domain.user.RegisterDTO;
import com.app.norway.domain.user.User;
import com.app.norway.infra.security.TokenService;
import com.app.norway.respositories.UserRepository;
import com.app.norway.services.InMemoryTokenBlacklist;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    InMemoryTokenBlacklist tokenBlacklist = new InMemoryTokenBlacklist();

    @Autowired
    private AuthenticationManager authenticationManager;

    private final SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthDTO data) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
            var auth = authenticationManager.authenticate(usernamePassword);

            var token = tokenService.gerenateToken((User) auth.getPrincipal());

            return ResponseEntity.ok(new LoginResponseDTO(token));
        } catch (Exception e) {
            logger.error("Error during login for user '{}': {}", data.login(), e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Valid RegisterDTO data) {
        try {
            if (userRepository.findByLogin(data.login()) != null) {
                logger.warn("Attempt to register user with existing login '{}'", data.login());
                return ResponseEntity.badRequest().build();
            }

            String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
            User user = new User(data.login(), encryptedPassword, data.role());
            userRepository.save(user);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error registering user '{}': {}", data.login(), e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        try {
            String token = tokenBlacklist.extractTokenFromRequest(request);
            tokenBlacklist.addToBlacklist(token);
            return ResponseEntity.ok("Logged out successfully");
        } catch (Exception e) {
            logger.error("Error during logout: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during logout");
        }
    }
}