package com.coolbank.model;

public record ServiceAuthRequest(String serviceName, String serviceId, String serviceSecret) {
    @Override
    public String toString() {
        return "ServiceAuthRequest{" +
                "serviceName='" + serviceName + '\'' +
                ", serviceId='" + serviceId + '\'' +
                ", serviceSecret='" + serviceSecret + '\'' +
                '}';
    }
}
