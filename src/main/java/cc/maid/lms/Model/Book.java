package cc.maid.lms.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
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
    @NotBlank(message = "Price cannot be empty")
    private double price;

    private String genre;

    @OneToMany(mappedBy = "book")
    private List<BorrowingRecord> borrowingRecords;

}
