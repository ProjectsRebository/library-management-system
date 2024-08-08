package com.library.management.system.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import com.library.management.system.dto.BookDto;
import com.library.management.system.dto.BorrowingRecordDto;
import com.library.management.system.models.Book;
import com.library.management.system.models.BorrowingRecord;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class BookEntityToBookDtoConverterTest {

    @InjectMocks
    private  BookEntityToBookDtoConverter converter ;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void convert() {
        // Prepare sample data
        BorrowingRecord borrowingRecord1 = BorrowingRecord.builder()
                .borrowingDate(LocalDate.of(2024, 1, 15))
                .returnDate(LocalDate.of(2024, 2, 15))
                .build();

        BorrowingRecord borrowingRecord2 = BorrowingRecord.builder()
                .borrowingDate(LocalDate.of(2024, 3, 10))
                .returnDate(LocalDate.of(2024, 4, 10))
                .build();

        Book book = Book.builder()
                .id(1L)
                .author("Author Name")
                .isbn("1234567890")
                .publicationYear(2023)
                .title("Book Title")
                .borrowingRecords(Arrays.asList(borrowingRecord1, borrowingRecord2))
                .build();

        // Convert Book to BookDto
        BookDto bookDto = converter.convert(book);

        // Verify conversion
        Assertions.assertNotNull(bookDto);
        Assertions.assertEquals(book.getId(), bookDto.getId());
        Assertions.assertEquals(book.getAuthor(), bookDto.getAuthor());
        Assertions.assertEquals(book.getIsbn(), bookDto.getIsbn());
        Assertions.assertEquals(book.getPublicationYear(), bookDto.getPublicationYear());
        Assertions.assertEquals(book.getTitle(), bookDto.getTitle());

        // Verify borrowing records conversion
        List<BorrowingRecordDto> borrowingRecordDtos = bookDto.getBorrowingRecords();
        Assertions.assertNotNull(borrowingRecordDtos);
        Assertions.assertEquals(2, borrowingRecordDtos.size());

        BorrowingRecordDto dto1 = borrowingRecordDtos.get(0);
        Assertions.assertEquals(borrowingRecord1.getBorrowingDate(), dto1.getBorrowingDate());
        Assertions.assertEquals(borrowingRecord1.getReturnDate(), dto1.getReturnDate());

        BorrowingRecordDto dto2 = borrowingRecordDtos.get(1);
        Assertions.assertEquals(borrowingRecord2.getBorrowingDate(), dto2.getBorrowingDate());
        Assertions.assertEquals(borrowingRecord2.getReturnDate(), dto2.getReturnDate());
    }

    @Test
    void convertWithEmptyBorrowingRecords() {
        Book book = Book.builder()
                .id(1L)
                .author("Author Name")
                .isbn("1234567890")
                .publicationYear(2023)
                .title("Book Title")
                .borrowingRecords(Collections.emptyList())
                .build();

        BookDto bookDto = converter.convert(book);

        Assertions.assertNotNull(bookDto);
        Assertions.assertEquals(book.getId(), bookDto.getId());
        Assertions.assertEquals(book.getAuthor(), bookDto.getAuthor());
        Assertions.assertEquals(book.getIsbn(), bookDto.getIsbn());
        Assertions.assertEquals(book.getPublicationYear(), bookDto.getPublicationYear());
        Assertions.assertEquals(book.getTitle(), bookDto.getTitle());
        Assertions.assertTrue(bookDto.getBorrowingRecords().isEmpty());
    }
}
