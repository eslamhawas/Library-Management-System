package cc.maid.lms.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


public record PatronDTO(@NotBlank(message = "Username cannot be empty")
                        String name,
                        @Email(message = "Please enter a valid email")
                        @NotBlank(message = "Email cannot be empty")
                        String email,
                        @NotBlank(message = "Phone number cannot be empty")
                        String phone,
                        @NotBlank(message = "Address cannot be empty")
                        String address) {
}