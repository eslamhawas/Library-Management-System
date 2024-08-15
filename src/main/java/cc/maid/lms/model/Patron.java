package cc.maid.lms.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Patron {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username cannot be empty")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Email cannot be empty")
    @Column(nullable = false)
    @Email(message = "Please enter a valid email")
    private String email;

    @NotBlank(message = "Phone number cannot be empty")
    @Column(nullable = false)
    private String phone;

    @NotBlank(message = "Address cannot be empty")
    @Column(nullable = false)
    private String address;

    @OneToMany(mappedBy = "patron")
    @JsonIgnore
    private List<BorrowingRecord> borrowingRecords;


}
