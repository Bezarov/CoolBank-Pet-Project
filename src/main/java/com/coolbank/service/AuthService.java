package com.coolbank.service;

public interface AuthService {
    String authenticateUser(String email, String password);

    String authenticateService(String serviceName, String serviceId, String serviceSecret);
}
