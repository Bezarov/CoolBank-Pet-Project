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
    public ResponseEntity<UsersDTO> getUserById(@PathVariable UUID userId) {
        UsersDTO usersDTO = usersService.getUserById(userId);
        return ResponseEntity.ok(usersDTO);
    }

    @GetMapping("/email/{userEmail}")
    public ResponseEntity<UsersDTO> getUserByEmail(@PathVariable String userEmail) {
        UsersDTO usersDTO = usersService.getUserByEmail(userEmail);
        return ResponseEntity.ok(usersDTO);
    }

    @GetMapping("/name/{userFullName}")
    public ResponseEntity<UsersDTO> getUserByFullName(@PathVariable String userFullName) {
        UsersDTO usersDTO = usersService.getUserByFullName(userFullName);
        return ResponseEntity.ok(usersDTO);
    }

    @GetMapping("/phone/{userPhoneNumber}")
    public ResponseEntity<UsersDTO> getUserByPhoneNumber(@PathVariable String userPhoneNumber) {
        UsersDTO usersDTO = usersService.getUserByPhoneNumber(userPhoneNumber);
        return ResponseEntity.ok(usersDTO);
    }

    @PutMapping("/update/")
    public ResponseEntity<UsersDTO> updateUser(@RequestBody UsersDTO usersDTO) {
        UsersDTO ResponseUsersDTO = usersService.updateUser(usersDTO);
        return ResponseEntity.ok(ResponseUsersDTO);
    }

    @PatchMapping("/update/{userId}/{newPassword}")
    public ResponseEntity<UsersDTO> updatePasswordById(@PathVariable UUID userId,
                                                       @PathVariable String newPassword) {
        UsersDTO usersDTO = usersService.updatePasswordById(userId, newPassword);
        return ResponseEntity.ok(usersDTO);
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
