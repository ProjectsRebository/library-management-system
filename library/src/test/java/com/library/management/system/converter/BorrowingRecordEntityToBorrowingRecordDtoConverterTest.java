package com.library.management.system.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.library.management.system.dto.BorrowingRecordDto;
import com.library.management.system.models.BorrowingRecord;

import java.time.LocalDate;

class BorrowingRecordEntityToBorrowingRecordDtoConverterTest {

    @InjectMocks
    private BorrowingRecordEntityToBorrowingRecordDtoConverter converter;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void convert() {
        // Prepare sample data
        LocalDate borrowingDate = LocalDate.of(2024, 1, 15);
        LocalDate returnDate = LocalDate.of(2024, 2, 15);

        BorrowingRecord borrowingRecord = BorrowingRecord.builder()
                .borrowingDate(borrowingDate)
                .returnDate(returnDate)
                .build();

        // Convert BorrowingRecord to BorrowingRecordDto
        BorrowingRecordDto borrowingRecordDto = converter.convert(borrowingRecord);

        // Verify conversion
        Assertions.assertNotNull(borrowingRecordDto);
        Assertions.assertEquals(borrowingDate, borrowingRecordDto.getBorrowingDate());
        Assertions.assertEquals(returnDate, borrowingRecordDto.getReturnDate());
    }

}
