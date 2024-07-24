package com.coolbank.model;

public record UserAuthRequest(String email, String password) {

    @Override
    public String toString() {
        return "UserAuthRequest{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
