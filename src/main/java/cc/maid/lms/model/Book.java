package cc.maid.lms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false,length = 18)
    @NotBlank(message = "ISBN cannot be empty")
    @Pattern(
            regexp = "^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?$)[\\d-]+$",
            message = "Invalid ISBN format"
    )
    private String ISBN;

    @Column(nullable = false)
    @NotBlank(message = "Title cannot be empty")
    private String title;


    @Column(nullable = false)
    @NotBlank(message = "Author cannot be empty")
    private String author;

    private String publisher;

    private LocalDate publishedDate;

    @Column(nullable = false)
    private double price;

    private String genre;

    @OneToMany(mappedBy = "book")
    @JsonIgnore
    private List<BorrowingRecord> borrowingRecords;

}
