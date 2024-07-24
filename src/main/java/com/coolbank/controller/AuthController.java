package com.coolbank.controller;

import com.coolbank.model.AuthResponse;
import com.coolbank.model.ServiceAuthRequest;
import com.coolbank.model.UserAuthRequest;
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
    public ResponseEntity<String> authenticateUser(@RequestBody UserAuthRequest userAuthRequest) {
        logger.debug("Received POST request to Authenticate User with Users Credentials: {}", userAuthRequest);
        String token = authService.authenticateUser(userAuthRequest.email(), userAuthRequest.password());
        return ResponseEntity.ok("Authentication successfully!" +
                "\nPlease use this JWT Token for further Access \n" + new AuthResponse(token));
    }

    @PostMapping("/service")
    public ResponseEntity<AuthResponse> authenticateService(@RequestBody ServiceAuthRequest serviceAuthRequest) {
        logger.debug("Received POST request to Authenticate Service with " +
                "Service Credentials: {}", serviceAuthRequest);
        String token = authService.authenticateService(serviceAuthRequest.serviceName(),
                serviceAuthRequest.serviceId(), serviceAuthRequest.serviceSecret());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
