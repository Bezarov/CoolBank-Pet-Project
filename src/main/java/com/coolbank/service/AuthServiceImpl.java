package com.coolbank.service;

import com.coolbank.security.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthServiceImpl implements AuthService {
    private final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public String authenticateUser(String email, String password) {
        try {
            logger.info("Authenticating user with email: {}", email);
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            logger.info("Authentication successfully for Email: {} and Password: {}", email, password);

            logger.info("Setting up Authentication context");
            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            logger.info("Received UserDetails: {} to JWT Utils", userDetails);
            String token = jwtUtil.userGenerateToken(userDetails);
            logger.info("Generated JWT Token: {}", token);
            return token;
        } catch (AuthenticationException error) {
            logger.error("Authentication failed for User with Email: {} Password {} Error: {}",
                    email, password, error);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Authentication failed \nInvalid Email or Password", error);
        }
    }

    @Override
    public String authenticateService(String serviceName, String serviceId, String serviceSecret) {
        return null;
    }
}
