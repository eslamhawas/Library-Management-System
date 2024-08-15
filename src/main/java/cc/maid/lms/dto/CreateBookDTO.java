package cc.maid.lms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;


public record CreateBookDTO(
        @Pattern(message = "Invalid ISBN format", regexp = "^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?$)[\\d-]+$")
        @NotBlank(message = "ISBN cannot be empty")
        String ISBN,
        @NotBlank(message = "Title cannot be empty")
        String title,
        @NotBlank(message = "Author cannot be empty")
        String author,
        String publisher, LocalDate publishedDate,
        double price, String genre){
}