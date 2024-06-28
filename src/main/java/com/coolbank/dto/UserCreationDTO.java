package com.coolbank.dto;

public class UserCreationDTO {
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    public UserCreationDTO(String password, String firstName, String lastName, String email, String phoneNumber) {
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }


}
