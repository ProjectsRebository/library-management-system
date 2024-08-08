package com.library.management.system.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.management.system.models.BorrowingRecord;

public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord,Long> {
    Optional<BorrowingRecord> findByBookIdAndPatronIdAndReturnDateIsNull(Long bookId, Long patronId);
}
