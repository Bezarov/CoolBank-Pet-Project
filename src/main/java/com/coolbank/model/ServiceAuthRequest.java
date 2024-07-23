package com.coolbank.model;

public class ServiceAuthRequest {
    private String serviceId;
    private String serviceSecret;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceSecret() {
        return serviceSecret;
    }

    public void setServiceSecret(String serviceSecret) {
        this.serviceSecret = serviceSecret;
    }

    @Override
    public String toString() {
        return "ServiceAuthRequest{" +
                "serviceId='" + serviceId + '\'' +
                ", serviceSecret='" + serviceSecret + '\'' +
                '}';
    }
}
