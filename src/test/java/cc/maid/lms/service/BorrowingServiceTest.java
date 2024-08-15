package cc.maid.lms.service;

import cc.maid.lms.exception.RecordNotFoundException;
import cc.maid.lms.model.Book;
import cc.maid.lms.model.BorrowingRecord;
import cc.maid.lms.model.Patron;
import cc.maid.lms.repository.BorrowingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BorrowingServiceTest {

    @Mock
    private BorrowingRepository borrowingRepository;

    @InjectMocks
    private BorrowingService borrowingService;

    private BorrowingRecord borrowingRecord;
    private Patron patron;
    private Book book;

    @BeforeEach
    void setUp() {
        patron = new Patron();
        patron.setId(1L);

        book = new Book();
        book.setId(1L);

        borrowingRecord = BorrowingRecord.builder()
                .id(UUID.randomUUID().toString())
                .patron(patron)
                .book(book)
                .borrowDate(LocalDate.now())
                .dueDate(LocalDate.now().plusWeeks(2))
                .build();
    }


    @Test
    @DisplayName("Add should successfully save a new borrowing record")
    public void add_ShouldSaveNewBorrowingRecord() {
        when(borrowingRepository.save(any(BorrowingRecord.class))).thenReturn(borrowingRecord);

        BorrowingRecord savedRecord = borrowingService.add(patron.getId(), book.getId());

        assertThat(savedRecord).isNotNull();
        assertThat(savedRecord.getPatron()).isEqualTo(patron);
        assertThat(savedRecord.getBook()).isEqualTo(book);
        assertThat(savedRecord.getBorrowDate()).isEqualTo(LocalDate.now());
        assertThat(savedRecord.getDueDate()).isEqualTo(LocalDate.now().plusWeeks(2));
        verify(borrowingRepository, times(1)).save(any(BorrowingRecord.class));
    }

    @Test
    @DisplayName("UpdateRecord should update the return date of an existing borrowing record")
    public void updateRecord_ShouldUpdateReturnDate() {
        BorrowingRecord existingRecord = BorrowingRecord.builder()
                .id(UUID.randomUUID().toString())
                .patron(patron)
                .book(book)
                .borrowDate(LocalDate.now())
                .dueDate(LocalDate.now().plusWeeks(2))
                .build();

        when(borrowingRepository.findByPatronIdAndBookId(patron.getId(), book.getId()))
                .thenReturn(Optional.of(existingRecord));
        when(borrowingRepository.save(existingRecord)).thenReturn(existingRecord);

        BorrowingRecord updatedRecord = borrowingService.updateRecord(patron.getId(), book.getId());

        assertThat(updatedRecord).isNotNull();
        assertThat(updatedRecord.getReturnDate()).isEqualTo(LocalDate.now());
        verify(borrowingRepository, times(1)).save(existingRecord);
    }

    @Test
    @DisplayName("UpdateRecord should throw RecordNotFoundException when record does not exist")
    public void updateRecord_ShouldThrowRecordNotFoundException_WhenRecordDoesNotExist() {
        Long nonExistentPatronId = 999L;
        Long nonExistentBookId = 999L;

        when(borrowingRepository.findByPatronIdAndBookId(nonExistentPatronId, nonExistentBookId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> borrowingService.updateRecord(nonExistentPatronId, nonExistentBookId))
                .isInstanceOf(RecordNotFoundException.class)
                .extracting(Throwable::getMessage)
                .isEqualTo("Record not found for patronId " + nonExistentPatronId + " and bookId " + nonExistentBookId);
    }
}
