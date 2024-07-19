package com.coolbank.service;

import com.coolbank.dto.UsersDTO;
import com.coolbank.model.Users;
import com.coolbank.repository.UsersRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class UsersServiceImpl implements UsersService {
    private static final Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);
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
        users.setCreatedDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        users.setStatus("ACTIVE");
        return users;
    }

    @Override
    public UsersDTO createUser(UsersDTO usersDTO) {
        logger.info("Attempting to find User with Email: {}", usersDTO.getEmail());
        usersRepository.findByEmail(usersDTO.getEmail())
                .ifPresent(EntityUser -> {
                    logger.error("User with such Email: {}, already exists", usersDTO.getEmail());
                    throw new ResponseStatusException(HttpStatus.FOUND,
                            "User with such Email ALREADY EXIST: " + usersDTO.getEmail());
                });
        Users user = usersRepository.save(convertUsersDTOToModel(usersDTO));
        logger.info("User created successfully: {}", user);
        return convertUsersModelToDTO(user);
    }

    @Override
    public UsersDTO getUserById(UUID userId) {
        logger.info("Attempting to find User with ID: {}", userId);
        return usersRepository.findById(userId)
                .map(UserEntity -> {
                    logger.info("User was found and received to the Controller: {}", UserEntity);
                    return convertUsersModelToDTO(UserEntity);
                })
                .orElseThrow(() -> {
                    logger.error("User with such ID: {} was not found", userId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "User with such ID was NOT Found: " + userId);
                });
    }

    @Override
    public UsersDTO getUserByEmail(String userEmail) {
        logger.info("Attempting to find User with Email: {}", userEmail);
        return usersRepository.findByEmail(userEmail)
                .map(UserEntity -> {
                    logger.info("User was found and received to the Controller: {}", UserEntity);
                    return convertUsersModelToDTO(UserEntity);
                })
                .orElseThrow(() -> {
                    logger.error("User with such Email: {} was not found", userEmail);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "User with such Email was NOT Found: " + userEmail);
                });
    }

    @Override
    public UsersDTO getUserByFullName(String userFullName) {
        logger.info("Attempting to find User with Name: {}", userFullName);
        return usersRepository.findByFullName(userFullName)
                .map(UserEntity -> {
                    logger.info("User was found and received to the Controller: {}", UserEntity);
                    return convertUsersModelToDTO(UserEntity);
                })
                .orElseThrow(() -> {
                    logger.error("User with such Name: {} was not found", userFullName);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "User with such FullName was NOT Found: " + userFullName);
                });
    }

    @Override
    public UsersDTO getUserByPhoneNumber(String userPhoneNumber) {
        logger.info("Attempting to find User with Phone Number: {}", userPhoneNumber);
        return usersRepository.findByPhoneNumber(userPhoneNumber)
                .map(UserEntity -> {
                    logger.info("User was found and received to the Controller: {}", UserEntity);
                    return convertUsersModelToDTO(UserEntity);
                })
                .orElseThrow(() -> {
                    logger.error("User with such Phone Number: {} was not found", userPhoneNumber);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "User with such PhoneNumber was NOT Found: " + userPhoneNumber);
                });
    }

    @Override
    public UsersDTO updateUser(UUID userId, UsersDTO usersDTO) {
        logger.info("Attempting to find User with ID: {}", userId);
        return usersRepository.findById(userId)
                .map(UserEntity -> {
                    UserEntity.setFullName(usersDTO.getFullName());
                    UserEntity.setEmail(usersDTO.getEmail());
                    UserEntity.setPhoneNumber(usersDTO.getPhoneNumber());
                    UserEntity.setPassword(usersDTO.getPassword());
                    usersRepository.save(UserEntity);
                    logger.info("User updated successfully: {}", UserEntity);
                    return convertUsersModelToDTO(UserEntity);
                })
                .orElseThrow(() -> {
                    logger.error("User with such ID: {} was not found", userId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "User with such ID was NOT Found: " + userId);
                });
    }

    @Override
    public UsersDTO updatePasswordById(UUID userId, String newPassword) {
        logger.info("Attempting to find User with ID: {}", userId);
        return usersRepository.findById(userId)
                .map(UserEntity -> {
                    UserEntity.setPassword(newPassword);
                    usersRepository.save(UserEntity);
                    logger.info("User password updated successfully: {}", UserEntity);
                    return convertUsersModelToDTO(UserEntity);
                })
                .orElseThrow(() -> {
                    logger.error("User with such ID: {} was not found", userId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "User with such ID was NOT Found: " + userId);
                });
    }

    @Transactional
    @Override
    public ResponseEntity<String> deleteUserById(UUID userId) {
        logger.info("Attempting to find User with ID: {}", userId);
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.error("User with such ID: {} was not found", userId);
                    return new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "User with such ID was NOT Found: " + userId);
                });
        usersRepository.deleteById(userId);
        logger.info("User was found and deleted successfully: {}", user);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.ACCEPTED);
    }

    @Transactional
    @Override
    public ResponseEntity<String> deleteUserByEmail(String userEmail) {
        logger.info("Attempting to find User with Email: {}", userEmail);
        Users user = usersRepository.findByEmail(userEmail)
                .orElseThrow(() -> {
                    logger.error("User with such Email: {} was not found", userEmail);
                    return new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "User with such Email was NOT Found: " + userEmail);
                });
        usersRepository.delete(user);
        logger.info("User was found and deleted successfully: {}", user);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.ACCEPTED);
    }

    @Transactional
    @Override
    public ResponseEntity<String> deleteUserByFullName(String userFullName) {
        logger.info("Attempting to find User with Name: {}", userFullName);
        Users user = usersRepository.findByFullName(userFullName)
                .orElseThrow(() -> {
                    logger.error("User with such Name: {} was not found", userFullName);
                    return new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "User with such Full Name was NOT Found: " + userFullName);
                });
        usersRepository.deleteByFullName(userFullName);
        logger.info("User was found and deleted successfully: {}", user);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.ACCEPTED);
    }
}