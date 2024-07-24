package com.coolbank.model;

public record AuthResponse(String token) {

    @Override
    public String toString() {
        return "Token=" + token;
    }
}
