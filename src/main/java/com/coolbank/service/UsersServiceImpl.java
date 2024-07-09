package com.coolbank.service;

import com.coolbank.dto.UsersDTO;
import com.coolbank.model.Users;
import com.coolbank.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        usersDTO.setId(user.getId());
        usersDTO.setFullName(user.getFullName());
        usersDTO.setEmail(user.getEmail());
        usersDTO.setPhoneNumber(user.getPhoneNumber());
        usersDTO.setPassword(user.getPassword());
        return usersDTO;
    }

    private Users convertUsersDTOToModel(UsersDTO usersDTO) {
        Users users = new Users();
        users.setFullName(usersDTO.getFullName());
        users.setEmail(usersDTO.getEmail());
        users.setPassword(usersDTO.getPassword());
        users.setPhoneNumber(usersDTO.getPhoneNumber());
        users.setId(usersDTO.getId());
        users.setCreatedDate(LocalDateTime.now());
        users.setStatus("ACTIVE");
        return users;
    }

    @Override
    public ResponseEntity<String> createUser(UsersDTO usersDTO) {
        usersRepository.findByEmail(usersDTO.getEmail())
                .ifPresent(EntityUser -> {
                    throw new ResponseStatusException(
                            HttpStatus.FOUND, "User with such Email ALREADY EXIST: " + usersDTO.getEmail());
                });
        usersRepository.save(convertUsersDTOToModel(usersDTO));
        return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
    }

    @Override
    public UsersDTO getUserById(UUID userId) {
        return usersRepository.findById(userId)
                .map(this::convertUsersModelToDTO)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User with such ID was NOT Found" + userId));
    }

    @Override
    public UsersDTO getUserByEmail(String userEmail) {
        return usersRepository.findByEmail(userEmail)
                .map(this::convertUsersModelToDTO)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User with such Email was NOT Found" + userEmail));
    }

    @Override
    public UsersDTO getUserByFullName(String userFullName) {
        return usersRepository.findByFullName(userFullName)
                .map(this::convertUsersModelToDTO)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User with such FullName was NOT Found" + userFullName));
    }

    @Override
    public UsersDTO getUserByPhoneNumber(String userPhoneNumber) {
        return usersRepository.findByPhoneNumber(userPhoneNumber)
                .map(this::convertUsersModelToDTO)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User with such PhoneNumber was NOT Found" + userPhoneNumber));
    }

    @Override
    public UsersDTO updateUser(UsersDTO usersDTO) {
        return usersRepository.findById(usersDTO.getId())
                .map(EntityUser -> {
                    EntityUser.setId(usersDTO.getId());
                    EntityUser.setFullName(usersDTO.getFullName());
                    EntityUser.setEmail(usersDTO.getEmail());
                    EntityUser.setPhoneNumber(usersDTO.getPhoneNumber());
                    EntityUser.setPassword(usersDTO.getPassword());
                    usersRepository.save(EntityUser);
                    return convertUsersModelToDTO(EntityUser);
                })
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User with such ID was NOT Found" + usersDTO.getId()));
    }

    @Override
    public UsersDTO updatePasswordById(UUID userId, String newPassword) {
        return usersRepository.findById(userId)
                .map(EntityUser -> {
                    EntityUser.setPassword(newPassword);
                    usersRepository.save(EntityUser);
                    return convertUsersModelToDTO(EntityUser);
                })
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User with such ID was NOT Found" + userId));
    }

    @Override
    public void deleteUserById(UUID userId) {
        usersRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User with such ID was NOT Found" + userId));
        usersRepository.deleteById(userId);
    }

    @Override
    public void deleteUserByEmail(String userEmail) {
        usersRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User with such ID was NOT Found" + userEmail));
        usersRepository.deleteByEmail(userEmail);
    }

    @Override
    public void deleteUserByFullName(String userFullName) {
        usersRepository.findByFullName(userFullName)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User with such ID was NOT Found" + userFullName));
        usersRepository.deleteByFullName(userFullName);
    }
}
