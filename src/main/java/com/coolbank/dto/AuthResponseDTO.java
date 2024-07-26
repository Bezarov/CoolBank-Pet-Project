package com.coolbank.dto;

public record AuthResponseDTO(String token) {

    @Override
    public String toString() {
        return "Token=" + token;
    }
}
