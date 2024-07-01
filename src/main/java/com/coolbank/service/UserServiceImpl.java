package com.coolbank.service;

import com.coolbank.dto.UsersDTO;
import com.coolbank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UsersDTO createUser(UsersDTO usersDTO) {
        return null;
    }

    @Override
    public UsersDTO getUserById(UUID userId) {
        return null;
    }

    @Override
    public UsersDTO getUserByEmail(String userEmail) {
        return null;
    }

    @Override
    public UsersDTO getUserByLastName(String userLastName) {
        return null;
    }

    @Override
    public UsersDTO getUserByPhoneNumber(String userPhoneNumber) {
        return null;
    }

    @Override
    public UsersDTO updateUser(UUID id, UsersDTO usersDTO) {
        return null;
    }

    @Override
    public UsersDTO updatePasswordById(UUID userId, String password) {
        return null;
    }

    @Override
    public void deleteUserById(UUID userId) {

    }

    @Override
    public void deleteUserByEmail(String userEmail) {

    }

    @Override
    public void deleteUserByLastName(String userLastName) {

    }
}
