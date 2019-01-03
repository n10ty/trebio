package com.trebio.context.account.service.dto;

public class RegisterUser {
    public String email;
    public String password;

    public RegisterUser(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
