package com.library.management.system.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.library.management.system.dto.BookDto;
import com.library.management.system.models.Book;

class BookDtoToBookEntityConverterTest {

    @InjectMocks
    private BookDtoToBookEntityConverter converter;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void convert() {
        BookDto bookDto = BookDto.builder()
                .author("Author Name")
                .isbn("1234567890")
                .publicationYear(2023)
                .title("Book Title")
                .build();

        Book book = converter.convert(bookDto);

        Assertions.assertNotNull(book);
        Assertions.assertEquals(bookDto.getAuthor(), book.getAuthor());
        Assertions.assertEquals(bookDto.getIsbn(), book.getIsbn());
        Assertions.assertEquals(bookDto.getPublicationYear(), book.getPublicationYear());
        Assertions.assertEquals(bookDto.getTitle(), book.getTitle());
    }
}

