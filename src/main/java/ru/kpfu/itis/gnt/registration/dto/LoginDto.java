package ru.kpfu.itis.gnt.registration.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;


import java.util.Objects;


public class LoginDto {
    @NotNull
    @Email
    private String email;
    @NotNull
    private String password;

    public LoginDto() {}

    public LoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoginDto)) return false;
        LoginDto loginDto = (LoginDto) o;
        return getEmail().equals(loginDto.getEmail()) && getPassword().equals(loginDto.getPassword());
    }

    @Override
    public String toString() {
        return "LoginDto{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail(), getPassword());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
