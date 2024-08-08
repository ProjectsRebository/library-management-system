package com.library.management.system.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.library.management.system.dto.BorrowingRecordDto;
import com.library.management.system.models.BorrowingRecord;

@Component
public class BorrowingRecordEntityToBorrowingRecordDtoConverter implements Converter<BorrowingRecord, BorrowingRecordDto> {

    @Override
    public BorrowingRecordDto convert(BorrowingRecord source) {
        return BorrowingRecordDto.builder()
                .borrowingDate(source.getBorrowingDate())
                .returnDate(source.getReturnDate())
                .build();
    }

}
