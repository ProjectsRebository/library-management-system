package com.library.management.system.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.library.management.system.dto.BookDto;
import com.library.management.system.dto.BorrowingRecordDto;
import com.library.management.system.models.Book;
import com.library.management.system.models.BorrowingRecord;

@Component
public class BookEntityToBookDtoConverter implements Converter<Book, BookDto> {

    @Override
    public BookDto convert(Book source) {
        return BookDto.builder()
                 .id(source.getId())
                .author(source.getAuthor())
                .isbn(source.getIsbn())
                .publicationYear(source.getPublicationYear())
                .title(source.getTitle())
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
