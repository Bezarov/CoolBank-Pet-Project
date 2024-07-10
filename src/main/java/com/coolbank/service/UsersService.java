package com.coolbank.service;

import com.coolbank.dto.UsersDTO;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface UsersService {
    ResponseEntity<String> createUser(UsersDTO usersDTO);

    UsersDTO getUserById(UUID userId);

    UsersDTO getUserByEmail(String userEmail);

    UsersDTO getUserByFullName(String userFullName);

    UsersDTO getUserByPhoneNumber(String userPhoneNumber);

    UsersDTO updateUser(UsersDTO usersDTO);

    UsersDTO updatePasswordById(UUID userId, String newPassword);

    void deleteUserById(UUID userId);

    void deleteUserByEmail(String userEmail);

    void deleteUserByFullName(String userFullName);
}
