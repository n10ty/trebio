package com.trebio.web.form.user;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class Registration {
    @Email(message = "Email should be valid")
    @NotNull
    public String email;

    @Length(min = 6, message = "Password length must be 6 characters or greater")
    public String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
