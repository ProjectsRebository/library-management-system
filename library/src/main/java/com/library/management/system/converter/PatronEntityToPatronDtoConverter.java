package com.library.management.system.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.library.management.system.dto.BorrowingRecordDto;
import com.library.management.system.dto.PatronDto;
import com.library.management.system.models.BorrowingRecord;
import com.library.management.system.models.Patron;

@Component
public class PatronEntityToPatronDtoConverter implements Converter<Patron, PatronDto> {

    @Override
    public PatronDto convert(Patron source) {
        return PatronDto.builder()
                .name(source.getName())
                .contactInformation(source.getContactInformation())
                .borrowingRecords(getBorrowingRecords(source.getBorrowingRecords()))
                .build();
    }

    private List<BorrowingRecordDto> getBorrowingRecords(List<BorrowingRecord> borrowingRecords) {
        return borrowingRecords.stream()
            .map(borrowingRecord -> BorrowingRecordDto.builder()
                .borrowingDate(borrowingRecord.getBorrowingDate())
                .returnDate(borrowingRecord.getReturnDate())
                .build())
            .collect(Collectors.toList());
    }
}
