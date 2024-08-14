package cc.maid.lms.Repository;

import cc.maid.lms.Model.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BorrowingRecordRepo extends JpaRepository<BorrowingRecord, String> {
    Optional<BorrowingRecord> findByPatronIdAndBookIsbn(Long patronId, Long bookIsbn);

}
