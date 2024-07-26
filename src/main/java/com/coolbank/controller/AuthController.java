package com.coolbank.controller;

import com.coolbank.dto.AuthResponseDTO;
import com.coolbank.dto.ComponentAuthRequestDTO;
import com.coolbank.dto.UserAuthRequestDTO;
import com.coolbank.service.AuthServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthServiceImpl authService;

    public AuthController(AuthServiceImpl authService) {
        this.authService = authService;
    }

    @PostMapping("/user")
    public ResponseEntity<String> authenticateUser(@RequestBody UserAuthRequestDTO userAuthRequestDTO) {
        logger.debug("Received POST request to Authenticate User with Users Credentials: {}", userAuthRequestDTO);
        String token = authService.authenticateUser(userAuthRequestDTO.email(), userAuthRequestDTO.password());
        return ResponseEntity.ok("Authentication successfully!" +
                "\nPlease use this JWT Token for further Access \n" + new AuthResponseDTO(token));
    }

    @PostMapping("/service")
    public ResponseEntity<AuthResponseDTO> authenticateService(
            @RequestBody ComponentAuthRequestDTO componentAuthRequestDTO) {
        logger.debug("Received POST request to Authenticate Service with " +
                "Service Credentials: {}", componentAuthRequestDTO);
        String token = authService.authenticateService(componentAuthRequestDTO.serviceName(),
                componentAuthRequestDTO.serviceId(), componentAuthRequestDTO.serviceSecret());
        return ResponseEntity.ok(new AuthResponseDTO(token));
    }
}
