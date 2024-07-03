package com.coolbank.service;

import com.coolbank.dto.UsersDTO;
import com.coolbank.model.Users;
import com.coolbank.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }


    private UsersDTO convertUsersModelToDTO(Users user) {
        UsersDTO usersDTO = new UsersDTO();
        usersDTO.setFullName(user.getFullName());
        usersDTO.setEmail(user.getEmail());
        usersDTO.setPhoneNumber(user.getEmail());
        return usersDTO;
    }

    private Users convertUsersDTOToModel(UsersDTO usersDTO) {
        Users users = new Users();
        users.setFullName(usersDTO.getFullName());
        users.setEmail(usersDTO.getEmail());
        users.setPassword(usersDTO.getPassword());
        users.setPhoneNumber(usersDTO.getPhoneNumber());
        users.setId(UUID.randomUUID());
        users.setCreatedDate(LocalDateTime.now());
        return users;
    }

    @Override
    public Users createUser(UsersDTO usersDTO) {
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
    public UsersDTO getUserByFullName(String userFullName) {
        return null;
    }

    @Override
    public UsersDTO getUserByPhoneNumber(String userPhoneNumber) {
        return null;
    }

    @Override
    public UsersDTO updateUser(UUID userId, UsersDTO usersDTO) {
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
