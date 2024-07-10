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
    public ResponseEntity<Void> deleteUserById(@PathVariable UUID userId) {
        usersService.deleteUserById(userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/remove/email/{userEmail}")
    public ResponseEntity<Void> deleteUserByEmail(@PathVariable String userEmail) {
        usersService.deleteUserByEmail(userEmail);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/remove/name/{userFullName}")
    public ResponseEntity<Void> deleteUserByFullName(@PathVariable String userFullName) {
        usersService.deleteUserByFullName(userFullName);
        return ResponseEntity.noContent().build();
    }


}
