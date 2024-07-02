package com.coolbank.service;

import com.coolbank.dto.UsersDTO;

import java.util.UUID;

public interface UserService {
    UsersDTO createUser(UsersDTO usersDTO);

    UsersDTO getUserById(UUID userId);

    UsersDTO getUserByEmail(String userEmail);

    UsersDTO getUserByLastName(String userLastName);

    UsersDTO getUserByPhoneNumber(String userPhoneNumber);

    UsersDTO updateUser(UUID id, UsersDTO usersDTO);

    UsersDTO updatePasswordById(UUID userId, String password);

    void deleteUserById(UUID userId);

    void deleteUserByEmail(String userEmail);

    void deleteUserByLastName(String userLastName);
}
