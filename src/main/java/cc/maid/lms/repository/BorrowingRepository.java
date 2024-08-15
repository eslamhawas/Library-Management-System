package cc.maid.lms.repository;

import cc.maid.lms.model.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BorrowingRepository extends JpaRepository<BorrowingRecord, String> {

    Optional<BorrowingRecord> findByPatronIdAndBookId(Long patronId, Long bookId);
}
