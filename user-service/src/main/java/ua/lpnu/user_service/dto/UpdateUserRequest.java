package ua.lpnu.user_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UpdateUserRequest {

    @NotBlank(message = "name is required")
    private String name;

    @Email(message = "email must be valid")
    @NotBlank(message = "email is required")
    private String email;

    public UpdateUserRequest() {
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}