package ru.kpfu.itis.gnt.registration.dto;

import jakarta.validation.constraints.*;
import ru.kpfu.itis.gnt.registration.utils.validation.PasswordsEqual;

import java.util.Objects;

@PasswordsEqual
public class UserDto {

    private Long id;

    @NotNull
    @Size(min = 2, max = 40)
    private String firstName;
    @NotNull
    @Size(min = 2, max = 40)
    private String lastName;
    @NotNull
    @Pattern(regexp = "\\d{4}-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])*")
    private String birthDate;
    @NotNull
    @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")

    @NotEmpty(message = "{email.notempty}")
    @Email
    private String email;
    @NotNull
    @Size(min = 2, max = 40)
    private String country;
    @NotNull
    @AssertTrue(message = "You need to accept the policy agreement")
    private Boolean policyAgreement;

    @NotNull
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
            message = "Password should contain at least 8 characters, 1 letter and 1 number")
    private String password;

    @NotNull
    private String passwordRepeat;

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", email='" + email + '\'' +
                ", country='" + country + '\'' +
                ", policyAgreement=" + policyAgreement +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto that = (UserDto) o;
        return Objects.equals(id, that.id) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(birthDate, that.birthDate) && Objects.equals(email, that.email) && Objects.equals(country, that.country) && Objects.equals(policyAgreement, that.policyAgreement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, birthDate, email, country, policyAgreement);
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

    public void setLastName(String secondName) {
        this.lastName = secondName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Boolean getPolicyAgreement() {
        return policyAgreement;
    }

    public void setPolicyAgreement(Boolean policyAgreement) {
        this.policyAgreement = policyAgreement;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
