package com.coolbank.service;

import com.coolbank.dto.AuthRequestDTO;
import com.coolbank.model.AppComponent;
import com.coolbank.model.Users;

public interface AuthDetailsService {

    void authenticateUser(AuthRequestDTO authRequestDTO);

    void authenticateComponent(AuthRequestDTO authRequestDTO);

    Users authenticateUserToken(String principal);

    AppComponent authenticateComponentToken(String principal);
}
