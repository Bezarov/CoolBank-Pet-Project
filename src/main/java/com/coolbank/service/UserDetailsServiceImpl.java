package com.coolbank.service;

import com.coolbank.model.Users;
import com.coolbank.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsersRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    public UserDetailsServiceImpl(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.info("Attempting to find User with Email: {}", email);
        Users user = userRepository.findByEmail(email).orElseThrow(() -> {
            logger.error("User with such Email: {} NOT FOUND", email);
            return new UsernameNotFoundException("User with such Email was NOT Found: " + email);
        });
        logger.info("User was found and received to AuthServiceImpl: {}", user);
        return new User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }
}