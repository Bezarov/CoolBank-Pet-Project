package com.coolbank.dto;

public record ComponentAuthRequestDTO(String serviceName, String serviceId, String serviceSecret) {
    @Override
    public String toString() {
        return "ComponentAuthRequestDTO{" +
                "serviceName='" + serviceName + '\'' +
                ", serviceId='" + serviceId + '\'' +
                ", serviceSecret='" + serviceSecret + '\'' +
                '}';
    }
}
