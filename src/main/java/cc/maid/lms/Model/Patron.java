package cc.maid.lms.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Patron {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username cannot be empty")
    private String name;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Please enter a valid email")
    private String email;

    @NotBlank(message = "Phone number cannot be empty")
    private String phone;

    @NotBlank(message = "Address cannot be empty")
    private String address;

    @OneToMany(mappedBy = "patron")
    private List<BorrowingRecord> borrowingRecords;


}
