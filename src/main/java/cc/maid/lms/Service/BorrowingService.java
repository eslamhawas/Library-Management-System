package cc.maid.lms.Service;

import cc.maid.lms.Exception.RecordNotFoundException;
import cc.maid.lms.Model.Book;
import cc.maid.lms.Model.BorrowingRecord;
import cc.maid.lms.Model.Patron;
import cc.maid.lms.Repository.BorrowingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class BorrowingService {

    private BorrowingRepo _borrowingRepo;

    @Autowired
    public BorrowingService(BorrowingRepo borrowingRepo) {

        _borrowingRepo = borrowingRepo;
    }

    @Transactional(rollbackFor = DataIntegrityViolationException.class)
    public BorrowingRecord add(Long patronId, Long bookId) {
        BorrowingRecord record = new BorrowingRecord();
        record.setId(UUID.randomUUID().toString());

        Patron patron = new Patron();
        patron.setId(patronId);
        record.setPatron(patron);

        Book book = new Book();
        book.setId(bookId);
        record.setBook(book);

        record.setBorrowDate(LocalDate.now());
        record.setDueDate(LocalDate.now().plusWeeks(2));

        return _borrowingRepo.save(record);
    }

    @Transactional(rollbackFor = Exception.class)
    public BorrowingRecord updateRecord(Long patronId, Long bookId) {
        BorrowingRecord existingRecord = _borrowingRepo.findByPatronIdAndBookId(patronId, bookId)
                .orElseThrow(() -> new RecordNotFoundException("Record not found for patronId " + patronId + " and bookId " + bookId));

        existingRecord.setReturnDate(LocalDate.now());

        return _borrowingRepo.save(existingRecord);
    }


}
