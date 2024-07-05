package com.coolbank.service;

import com.coolbank.dto.UsersDTO;
import com.coolbank.model.Users;

import java.util.UUID;

public interface UsersService {
    Users createUser(UsersDTO usersDTO);

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
