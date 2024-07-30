package com.coolbank.service;


import com.coolbank.dto.AuthRequestDTO;

public interface AuthService {
    String authenticateUser(AuthRequestDTO authRequestDTO);

    String authenticateComponent(AuthRequestDTO authRequestDTO);
}
