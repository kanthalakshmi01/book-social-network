package com.kl.book.auth;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
public class RegistrationRequest {

    @NotEmpty(message = "First Name is mandatory")
    @NotBlank(message = "First Name is mandatory")
    private String firstname;
    @NotEmpty(message = "Last Name is mandatory")
    @NotBlank(message = "Last Name is mandatory")
    private String lastname;
    @Email(message = "Email is not formatted")
    @NotEmpty(message = "Email is mandatory")
    @NotBlank(message = "Email is mandatory")
    private String email;

    public @NotEmpty(message = "Password is mandatory") @NotBlank(message = "Password is mandatory") @Size(min = 8, message = "Password should be 8 characters long minimum") String getPassword() {
        return password;
    }

    public void setPassword(@NotEmpty(message = "Password is mandatory") @NotBlank(message = "Password is mandatory") @Size(min = 8, message = "Password should be 8 characters long minimum") String password) {
        this.password = password;
    }

    public @Email(message = "Email is not formatted") @NotEmpty(message = "Email is mandatory") @NotBlank(message = "Email is mandatory") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "Email is not formatted") @NotEmpty(message = "Email is mandatory") @NotBlank(message = "Email is mandatory") String email) {
        this.email = email;
    }

    public @NotEmpty(message = "Last Name is mandatory") @NotBlank(message = "Last Name is mandatory") String getLastname() {
        return lastname;
    }

    public void setLastname(@NotEmpty(message = "Last Name is mandatory") @NotBlank(message = "Last Name is mandatory") String lastname) {
        this.lastname = lastname;
    }

    public @NotEmpty(message = "First Name is mandatory") @NotBlank(message = "First Name is mandatory") String getFirstname() {
        return firstname;
    }

    public void setFirstname(@NotEmpty(message = "First Name is mandatory") @NotBlank(message = "First Name is mandatory") String firstname) {
        this.firstname = firstname;
    }

    @NotEmpty(message = "Password is mandatory")
    @NotBlank(message = "Password is mandatory")
    @Size(min = 8,message = "Password should be 8 characters long minimum")
    private String password;

}
