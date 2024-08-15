package cc.maid.lms.controller;


import cc.maid.lms.model.BorrowingRecord;
import cc.maid.lms.service.BorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api")
public class BorrowingController {

    private BorrowingService borrowingService;

    @Autowired
    public BorrowingController(BorrowingService borrowingService) {
        this.borrowingService = borrowingService;
    }

    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecord> createRecord(@PathVariable Long bookId,
                                                        @PathVariable Long patronId) {


        BorrowingRecord record = borrowingService.add(patronId, bookId);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(record.getId())
                .toUri();

        return ResponseEntity.created(location).body(record);
    }

    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecord> updateRecord(@PathVariable Long bookId,
                                                        @PathVariable Long patronId){
        BorrowingRecord record = borrowingService.updateRecord(patronId, bookId);
        return ResponseEntity.ok(record);
    }
}
