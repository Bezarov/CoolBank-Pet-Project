package com.coolbank.dto;

public record UserAuthRequestDTO(String email, String password) {

    @Override
    public String toString() {
        return "UserAuthRequestDTO{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
