package com.coolbank.service;

public interface AuthService {
    String authenticateUser(String username, String password);

    String authenticateService(String serviceId, String serviceSecret);
}
