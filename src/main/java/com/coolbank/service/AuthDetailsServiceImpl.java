package com.coolbank.service;

import com.coolbank.dto.AuthRequestDTO;
import com.coolbank.model.AppComponent;
import com.coolbank.model.Users;
import com.coolbank.repository.AppComponentRepository;
import com.coolbank.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class AuthDetailsServiceImpl implements AuthDetailsService{
    private final Logger logger = LoggerFactory.getLogger(AuthDetailsServiceImpl.class);
    private final UsersRepository userRepository;
    private final AppComponentRepository componentRepository;

    public AuthDetailsServiceImpl(UsersRepository userRepository, AppComponentRepository componentRepository) {
        this.userRepository = userRepository;
        this.componentRepository = componentRepository;
    }

    public void authenticateUser(AuthRequestDTO authRequestDTO) {
        logger.info("Attempting to find user with email: {} in DB", authRequestDTO.principal());
        userRepository.findByEmail(authRequestDTO.principal().toString())
                .filter(UserEntity ->
                        UserEntity.getEmail().equals(authRequestDTO.principal().toString()) &&
                                passwordEncoder().matches(authRequestDTO.credentials().toString(), UserEntity.getPassword()))
                .orElseThrow(() -> {
                    logger.error("User with such Email: {} NOT FOUND in DB", authRequestDTO.principal());
                    return new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                            "Authentication failed: Invalid user credentials");
                });
        logger.info("User successfully authenticated: {}", authRequestDTO);
    }

    public Users authenticateUserToken(String principal) {
        logger.info("Attempting to find User with email: {}", principal);
        Users user = userRepository.findByEmail(principal)
                .orElseThrow(() -> {
                    logger.error("User Email extracted from the Token was NOT found in DB: {} ", principal);
                    return new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                            "Invalid Token credentials");
                });

        logger.info("User Token successfully authenticated: {}", user);
        return user;
    }

    public void authenticateComponent(AuthRequestDTO authRequestDTO) {
        logger.info("Attempting to find Component with ID: {}", authRequestDTO.principal());
        componentRepository.findById(UUID.fromString(authRequestDTO.principal().toString()))
                .filter(componentEntity -> componentEntity.getComponentId().toString().equals(
                        authRequestDTO.principal().toString()) &&
                        passwordEncoder().matches(
                                authRequestDTO.credentials().toString(), componentEntity.getComponentSecret())
                )
                .orElseThrow(() -> {
                    logger.error("Component: {} NOT FOUND in DB ", authRequestDTO);
                    return new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                            "Authentication failed: Invalid component credentials");
                });
        logger.info("Component successfully authenticated: {}", authRequestDTO);
    }

    public AppComponent authenticateComponentToken(String principal) {
        logger.info("Attempting to find Component with ID: {}", principal);
        AppComponent component = componentRepository.findById(UUID.fromString(principal))
                .orElseThrow(() -> {
                    logger.error("Component ID extracted from the Token was NOT found in DB: {} ", principal);
                    return new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                            "Invalid Token credentials");
                });

        logger.info("Component Token successfully authenticated: {}", principal);
        return component;
    }

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
