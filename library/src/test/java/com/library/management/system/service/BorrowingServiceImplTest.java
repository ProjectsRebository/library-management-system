package com.library.management.system.service;

import java.time.LocalDate;
import java.util.Optional;

import com.library.management.system.service.impl.BorrowingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.library.management.system.exception.NotFoundException;
import com.library.management.system.models.Book;
import com.library.management.system.models.BorrowingRecord;
import com.library.management.system.models.Patron;
import com.library.management.system.repository.BookRepository;
import com.library.management.system.repository.BorrowingRecordRepository;
import com.library.management.system.repository.PatronRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BorrowingServiceImplTest {

    @InjectMocks
    private BorrowingServiceImpl borrowingService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private PatronRepository patronRepository;

    @Mock
    private BorrowingRecordRepository borrowingRecordRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void borrowBookSuccessfullyTest() {
        // given
        Long bookId = 1L;
        Long patronId = 2L;
        Book book = Book.builder().id(bookId).title("Book Title").build();
        Patron patron = Patron.builder().id(patronId).name("Patron Name").build();
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(patronRepository.findById(patronId)).thenReturn(Optional.of(patron));

        // when
        borrowingService.borrowBook(bookId, patronId);

        // then
        verify(bookRepository, times(1)).findById(bookId);
        verify(patronRepository, times(1)).findById(patronId);
        verify(borrowingRecordRepository, times(1)).save(argThat(record ->
                record.getBook().equals(book) &&
                        record.getPatron().equals(patron) &&
                        record.getBorrowingDate().equals(LocalDate.now()) &&
                        record.getReturnDate() == null
        ));
    }

    @Test
    void borrowBookBookNotFoundTest() {
        // given
        Long bookId = 1L;
        Long patronId = 2L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // when
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> borrowingService.borrowBook(bookId, patronId));

        // then
        assertEquals("Book not found", thrown.getMessage());
        verify(bookRepository, times(1)).findById(bookId);
        verify(patronRepository, never()).findById(patronId);
        verify(borrowingRecordRepository, never()).save(any(BorrowingRecord.class));
    }

    @Test
    void borrowBookPatronNotFoundTest() {
        // given
        Long bookId = 1L;
        Long patronId = 2L;
        Book book = Book.builder().id(bookId).title("Book Title").build();
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(patronRepository.findById(patronId)).thenReturn(Optional.empty());

        // when
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> borrowingService.borrowBook(bookId, patronId));

        // then
        assertEquals("Patron not found", thrown.getMessage());
        verify(bookRepository, times(1)).findById(bookId);
        verify(patronRepository, times(1)).findById(patronId);
        verify(borrowingRecordRepository, never()).save(any(BorrowingRecord.class));
    }

    @Test
    void returnBookSuccessfullyTest() {
        // given
        Long bookId = 1L;
        Long patronId = 2L;
        BorrowingRecord record = BorrowingRecord.builder()
                .book(Book.builder().id(bookId).build())
                .patron(Patron.builder().id(patronId).build())
                .borrowingDate(LocalDate.now().minusDays(1))
                .returnDate(null)
                .build();
        when(borrowingRecordRepository.findByBookIdAndPatronIdAndReturnDateIsNull(bookId, patronId))
                .thenReturn(Optional.of(record));

        // when
        borrowingService.returnBook(bookId, patronId);

        // then
        verify(borrowingRecordRepository, times(1)).findByBookIdAndPatronIdAndReturnDateIsNull(bookId, patronId);
        verify(borrowingRecordRepository, times(1)).save(argThat(updatedRecord ->
                updatedRecord.getReturnDate().equals(LocalDate.now())
        ));
    }

    @Test
    void returnBookNotFoundTest() {
        // given
        Long bookId = 1L;
        Long patronId = 2L;
        when(borrowingRecordRepository.findByBookIdAndPatronIdAndReturnDateIsNull(bookId, patronId))
                .thenReturn(Optional.empty());

        // when
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> borrowingService.returnBook(bookId, patronId));

        // then
        assertEquals("Borrowing record not found", thrown.getMessage());
        verify(borrowingRecordRepository, times(1)).findByBookIdAndPatronIdAndReturnDateIsNull(bookId, patronId);
        verify(borrowingRecordRepository, never()).save(any(BorrowingRecord.class));
    }
}

