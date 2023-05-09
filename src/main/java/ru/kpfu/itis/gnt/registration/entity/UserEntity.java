package ru.kpfu.itis.gnt.registration.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import java.util.Objects;

public class UserEntity {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String birthDate;
    private String country;

    private String role;

    public UserEntity(String role, String firstName, String lastName, String email, String password, String birthDate, String country) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
        this.country = country;
        this.role = role;
    }

    public UserRole getRole() {
        if ("ADMIN".equals(role)) {
            return UserRole.ADMIN;
        }
        return UserRole.USER;
    }

    public void setRole(UserRole role) {
        if (role == UserRole.ADMIN) {
            this.role = "ADMIN";
        } else {
            this.role = "USER";
        }
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return id.equals(that.id) && firstName.equals(that.firstName) && lastName.equals(that.lastName) && email.equals(that.email) && password.equals(that.password) && birthDate.equals(that.birthDate) && country.equals(that.country) && role.equals(that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, password, birthDate, country, role);
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", country='" + country + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
