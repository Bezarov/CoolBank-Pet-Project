package com.coolbank.controller;

import com.coolbank.dto.UsersDTO;
import com.coolbank.service.UsersServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    private final UsersServiceImpl usersService;

    public UsersController(UsersServiceImpl usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> createUser(@RequestBody UsersDTO usersDTO) {
        return usersService.createUser(usersDTO);
    }

    @GetMapping("/id/{userId}")
    public UsersDTO getUserById(@PathVariable UUID userId) {
        return usersService.getUserById(userId);
    }

    @GetMapping("/email/{userEmail}")
    public UsersDTO getUserByEmail(@PathVariable String userEmail) {
        return usersService.getUserByEmail(userEmail);
    }

    @GetMapping("/name/{userFullName}")
    public UsersDTO getUserByFullName(@PathVariable String userFullName) {
        return usersService.getUserByFullName(userFullName);
    }

    @GetMapping("/phone/{userPhoneNumber}")
    public UsersDTO getUserByPhoneNumber(@PathVariable String userPhoneNumber) {
        return usersService.getUserByPhoneNumber(userPhoneNumber);
    }

    @PutMapping("/update/")
    public UsersDTO updateUser(@RequestBody UsersDTO usersDTO) {
        return usersService.updateUser(usersDTO);
    }

    @PatchMapping("/update/{userId}/{newPassword}")
    public UsersDTO updatePasswordById(@PathVariable UUID userId,
                                       @PathVariable String newPassword) {
        return usersService.updatePasswordById(userId, newPassword);
    }

    @DeleteMapping("/remove/id/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable UUID userId) {
        return usersService.deleteUserById(userId);
    }

    @DeleteMapping("/remove/email/{userEmail}")
    public ResponseEntity<String> deleteUserByEmail(@PathVariable String userEmail) {
        return usersService.deleteUserByEmail(userEmail);
    }

    @DeleteMapping("/remove/name/{userFullName}")
    public ResponseEntity<String> deleteUserByFullName(@PathVariable String userFullName) {
        return usersService.deleteUserByFullName(userFullName);
    }
}
