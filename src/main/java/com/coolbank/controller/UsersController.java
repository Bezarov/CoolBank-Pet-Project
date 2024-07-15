package com.coolbank.controller;

import com.coolbank.dto.UsersDTO;
import com.coolbank.service.UsersServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/api/users")
public class UsersController {
    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    private final UsersServiceImpl usersService;

    public UsersController(UsersServiceImpl usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/register")
    public ResponseEntity<UsersDTO> createUser(@RequestBody UsersDTO usersDTO) {
        logger.debug("Received POST request to create User with EMAIL {}", usersDTO.getEmail());
        return ResponseEntity.ok(usersService.createUser(usersDTO));
    }

    @GetMapping("/id/{userId}")
    public ResponseEntity<UsersDTO> getUserById(@PathVariable UUID userId) {
        logger.debug("Received GET request to get User by ID {}", userId);
        UsersDTO usersDTO = usersService.getUserById(userId);
        return ResponseEntity.ok(usersDTO);
    }

    @GetMapping("/email/{userEmail}")
    public ResponseEntity<UsersDTO> getUserByEmail(@PathVariable String userEmail) {
        logger.debug("Received GET request to get User by EMAIL {}", userEmail);
        UsersDTO usersDTO = usersService.getUserByEmail(userEmail);
        return ResponseEntity.ok(usersDTO);
    }

    @GetMapping("/name/{userFullName}")
    public ResponseEntity<UsersDTO> getUserByFullName(@PathVariable String userFullName) {
        logger.debug("Received GET request to get User by FULL NAME {}", userFullName);
        UsersDTO usersDTO = usersService.getUserByFullName(userFullName);
        return ResponseEntity.ok(usersDTO);
    }

    @GetMapping("/phone/{userPhoneNumber}")
    public ResponseEntity<UsersDTO> getUserByPhoneNumber(@PathVariable String userPhoneNumber) {
        logger.debug("Received GET request to get User by PHONE NUMBER {}", userPhoneNumber);
        UsersDTO usersDTO = usersService.getUserByPhoneNumber(userPhoneNumber);
        return ResponseEntity.ok(usersDTO);
    }

    @PutMapping("/update/")
    public ResponseEntity<UsersDTO> updateUser(@RequestBody UsersDTO usersDTO) {
        logger.debug("Received PUT request to update User with EMAIL {}", usersDTO.getEmail());
        UsersDTO ResponseUsersDTO = usersService.updateUser(usersDTO);
        return ResponseEntity.ok(ResponseUsersDTO);
    }

    @PatchMapping("/update/{userId}/{newPassword}")
    public ResponseEntity<UsersDTO> updatePasswordById(@PathVariable UUID userId,
                                                       @PathVariable String newPassword) {
        logger.debug("Received PATCH request to update Users password - {} with ID {}",
                newPassword, userId);
        UsersDTO usersDTO = usersService.updatePasswordById(userId, newPassword);
        return ResponseEntity.ok(usersDTO);
    }

    @DeleteMapping("/remove/id/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable UUID userId) {
        logger.debug("Received DELETE request to delete User by ID {}", userId);
        return usersService.deleteUserById(userId);
    }

    @DeleteMapping("/remove/email/{userEmail}")
    public ResponseEntity<String> deleteUserByEmail(@PathVariable String userEmail) {
        logger.debug("Received DELETE request to delete User by EMAIL {}", userEmail);
        return usersService.deleteUserByEmail(userEmail);
    }

    @DeleteMapping("/remove/name/{userFullName}")
    public ResponseEntity<String> deleteUserByFullName(@PathVariable String userFullName) {
        logger.debug("Received DELETE request to delete User by FULL NAME {}", userFullName);
        return usersService.deleteUserByFullName(userFullName);
    }
}
