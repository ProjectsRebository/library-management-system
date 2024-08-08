package com.library.management.system.converter;


import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.library.management.system.dto.BookDto;
import com.library.management.system.models.Book;

@Component
public class BookDtoToBookEntityConverter implements Converter<BookDto, Book> {

    @Override
    public Book convert(BookDto source) {
        return Book.builder()
                .author(source.getAuthor())
                .isbn(source.getIsbn())
                .publicationYear(source.getPublicationYear())
                .title(source.getTitle())
                .build();
    }
}
