package com.coolbank.service;

import com.coolbank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
//    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String authenticateUser(String username, String password) {
        return null;
    }

    @Override
    public String authenticateService(String serviceId, String serviceSecret) {
        return null;
    }
}
