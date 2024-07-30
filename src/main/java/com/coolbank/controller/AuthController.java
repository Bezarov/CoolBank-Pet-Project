package com.coolbank.controller;

import com.coolbank.dto.AuthRequestDTO;
import com.coolbank.dto.AuthResponseDTO;
import com.coolbank.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/user")
    public ResponseEntity<String> authenticateUser(@RequestBody AuthRequestDTO authRequestDTO) {
        logger.debug("Received POST request to Authenticate User with Users Credentials: {}", authRequestDTO);
        String token = authService.authenticateUser(authRequestDTO);
        return ResponseEntity.ok("Authentication successfully!" +
                "\nPlease use this JWT Token for further Access \n" + new AuthResponseDTO(token));
    }

    @PostMapping("/component")
    public ResponseEntity<AuthResponseDTO> authenticateComponent(@RequestBody AuthRequestDTO authRequestDTO) {
        logger.debug("Received POST request to Authenticate App Component with " +
                "Component Credentials: {}", authRequestDTO);
        String token = authService.authenticateComponent(authRequestDTO);
        return ResponseEntity.ok(new AuthResponseDTO(token));
    }
}
