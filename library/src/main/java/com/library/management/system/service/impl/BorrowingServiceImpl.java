package com.library.management.system.service.impl;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.library.management.system.exception.NotFoundException;
import com.library.management.system.models.Book;
import com.library.management.system.models.BorrowingRecord;
import com.library.management.system.models.Patron;
import com.library.management.system.repository.BookRepository;
import com.library.management.system.repository.BorrowingRecordRepository;
import com.library.management.system.repository.PatronRepository;
import com.library.management.system.service.BorrowingService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class BorrowingServiceImpl implements BorrowingService {

    private final BookRepository bookRepository;
    private final PatronRepository patronRepository;
    private final BorrowingRecordRepository borrowingRecordRepository;

    @Override
    public void borrowBook(Long bookId, Long patronId) {
        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new NotFoundException("Book not found"));
        Patron patron = patronRepository.findById(patronId)
            .orElseThrow(() -> new NotFoundException("Patron not found"));

        BorrowingRecord record = BorrowingRecord.builder()
            .book(book)
            .patron(patron)
            .borrowingDate(LocalDate.now())
            .returnDate(null) // Book is currently borrowed
            .build();

        borrowingRecordRepository.save(record);
    }

    @Override
    public void returnBook(Long bookId, Long patronId) {
        BorrowingRecord record = borrowingRecordRepository.findByBookIdAndPatronIdAndReturnDateIsNull(bookId, patronId)
            .orElseThrow(() -> new NotFoundException("Borrowing record not found"));
        record.setReturnDate(LocalDate.now());
        borrowingRecordRepository.save(record);
    }
}
