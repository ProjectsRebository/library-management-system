package com.library.management.system.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.library.management.system.dto.BorrowingRecordDto;
import com.library.management.system.dto.PatronDto;
import com.library.management.system.models.BorrowingRecord;
import com.library.management.system.models.Patron;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class PatronEntityToPatronDtoConverterTest {

    @InjectMocks
    private PatronEntityToPatronDtoConverter converter;

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

        Patron patron = Patron.builder()
                .name("aml fawzy")
                .contactInformation("aml.fawzy@example.com")
                .borrowingRecords(Arrays.asList(borrowingRecord1, borrowingRecord2))
                .build();

        // Convert Patron to PatronDto
        PatronDto patronDto = converter.convert(patron);

        // Verify conversion
        Assertions.assertNotNull(patronDto);
        Assertions.assertEquals(patron.getName(), patronDto.getName());
        Assertions.assertEquals(patron.getContactInformation(), patronDto.getContactInformation());

        // Verify borrowing records conversion
        List<BorrowingRecordDto> borrowingRecordDtos = patronDto.getBorrowingRecords();
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
        Patron patron = Patron.builder()
                .name("aml fawzy")
                .contactInformation("aml.fawzye@example.com")
                .borrowingRecords(Collections.emptyList())
                .build();

        PatronDto patronDto = converter.convert(patron);

        Assertions.assertNotNull(patronDto);
        Assertions.assertEquals(patron.getName(), patronDto.getName());
        Assertions.assertEquals(patron.getContactInformation(), patronDto.getContactInformation());
        Assertions.assertTrue(patronDto.getBorrowingRecords().isEmpty());
    }
}
