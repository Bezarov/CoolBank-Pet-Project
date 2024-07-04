package com.coolbank.service;

import com.coolbank.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final UsersRepository usersRepository;
//    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public AuthServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
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
