package com.resellerapp.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserRegisterDto {

    @NotNull
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotNull
    @Size(min = 3, max = 20)
    private String password;

    @NotNull
    private String confirmPassword;

    public @NotNull @Size(min = 3, max = 20) String getUsername() {
        return username;
    }

    public void setUsername(@NotNull @Size(min = 3, max = 20) String username) {
        this.username = username;
    }

    public @NotBlank @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank @Email String email) {
        this.email = email;
    }

    public @NotNull @Size(min = 3, max = 20) String getPassword() {
        return password;
    }

    public void setPassword(@NotNull @Size(min = 3, max = 20) String password) {
        this.password = password;
    }

    public @NotNull String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(@NotNull String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
