package cc.maid.lms.service;

import cc.maid.lms.exception.RecordNotFoundException;
import cc.maid.lms.model.Book;
import cc.maid.lms.model.BorrowingRecord;
import cc.maid.lms.model.Patron;
import cc.maid.lms.repository.BookRepository;
import cc.maid.lms.repository.BorrowingRepository;
import cc.maid.lms.repository.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
@Transactional(rollbackFor = {Exception.class})
public class BorrowingService {

    private BorrowingRepository borrowingRepository;
    private BookRepository bookRepository;
    private PatronRepository patronRepository;

    @Autowired
    public BorrowingService(BorrowingRepository borrowingRepository, BookRepository bookRepository, PatronRepository patronRepository) {

        this.borrowingRepository = borrowingRepository;
        this.bookRepository = bookRepository;
        this.patronRepository = patronRepository;
    }


    public BorrowingRecord add(Long patronId, Long bookId) {
        BorrowingRecord record = new BorrowingRecord();
        record.setId(UUID.randomUUID().toString());

        Patron patron =patronRepository.findById(patronId)
                .orElseThrow(()->new RecordNotFoundException("Cannot find patron with id " + patronId));
        record.setPatron(patron);

        Book book = bookRepository.findById(bookId)
                .orElseThrow(()->new RecordNotFoundException("Cannot find a book with id " + bookId));
        record.setBook(book);

        record.setBorrowDate(LocalDate.now());
        record.setDueDate(LocalDate.now().plusWeeks(2));

        return borrowingRepository.save(record);
    }

    public BorrowingRecord updateRecord(Long patronId, Long bookId) {
        BorrowingRecord existingRecord = borrowingRepository.findByPatronIdAndBookId(patronId, bookId)
                .orElseThrow(() -> new RecordNotFoundException("Record not found for patronId " + patronId + " and bookId " + bookId));

        existingRecord.setReturnDate(LocalDate.now());

        return borrowingRepository.save(existingRecord);
    }


}
