package com.kl.book.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AuthenticationRequest {


    @Email(message = "Email is not formatted")
    @NotEmpty(message = "Email is mandatory")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotEmpty(message = "Password is mandatory")
    @NotBlank(message = "Password is mandatory")
    @Size(min = 8,message = "Password should be 8 characters long minimum")
    private String password;

    public @Email(message = "Email is not formatted") @NotEmpty(message = "Email is mandatory") @NotBlank(message = "Email is mandatory") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "Email is not formatted") @NotEmpty(message = "Email is mandatory") @NotBlank(message = "Email is mandatory") String email) {
        this.email = email;
    }

    public @NotEmpty(message = "Password is mandatory") @NotBlank(message = "Password is mandatory") @Size(min = 8, message = "Password should be 8 characters long minimum") String getPassword() {
        return password;
    }

    public void setPassword(@NotEmpty(message = "Password is mandatory") @NotBlank(message = "Password is mandatory") @Size(min = 8, message = "Password should be 8 characters long minimum") String password) {
        this.password = password;
    }
}
