package cc.maid.lms.repository;


import cc.maid.lms.model.Book;
import cc.maid.lms.model.BorrowingRecord;
import cc.maid.lms.model.Patron;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class BorrowingRepositoryTest {
    @Autowired
    private BorrowingRepository borrowingRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private PatronRepository patronRepository;

    @Test
    public void findByPatronIdAndBookId_ShouldReturnRecord_WhenIdExists() {
        Book book = Book.builder().price(90).
                title("Clean Code").genre("Action")
                .author("Uncle bob").ISBN("1231231231")
                .build();

        Book savedBook = bookRepository.save(book);


        Patron patron = Patron.builder()
                .email("eslamsapry.hawwas10@gmail.com")
                .phone("01230").address("Egypt")
                .name("Eslam").build();

        Patron savedPatron = patronRepository.save(patron);

        BorrowingRecord record = BorrowingRecord.builder()
                .borrowDate(LocalDate.now())
                .dueDate(LocalDate.now().plusWeeks(2))
                .id(UUID.randomUUID().toString())
                .book(book).patron(patron).build();

        BorrowingRecord savedRecord = borrowingRepository.save(record);

        Assertions.assertThat(savedRecord)
                .isEqualTo(borrowingRepository.findByPatronIdAndBookId(savedPatron.getId(), savedBook.getId()).get());
    }


}

