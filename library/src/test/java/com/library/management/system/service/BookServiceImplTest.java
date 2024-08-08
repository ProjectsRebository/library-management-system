package com.library.management.system.service;

import java.util.List;
import java.util.Optional;

import com.library.management.system.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.library.management.system.converter.BookDtoToBookEntityConverter;
import com.library.management.system.converter.BookEntityToBookDtoConverter;
import com.library.management.system.dto.BookDto;
import com.library.management.system.dto.PagingDto;
import com.library.management.system.exception.NotFoundException;
import com.library.management.system.models.Book;
import com.library.management.system.repository.BookRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceImplTest {

    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookEntityToBookDtoConverter bookEntityToBookDtoConverter;

    @Mock
    private BookDtoToBookEntityConverter bookDtoToBookEntityConverter;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getBookByIDFoundTest() {
        // given
        Book book = Book.builder().id(1L).title("Book Title").build();
        BookDto bookDto = BookDto.builder().id(1L).title("Book Title").build();
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookEntityToBookDtoConverter.convert(book)).thenReturn(bookDto);

        // when
        BookDto result = bookService.getBookByID(1L);

        // then
        assertNotNull(result);
        assertEquals("Book Title", result.getTitle());
        verify(bookRepository, times(1)).findById(1L);
        verify(bookEntityToBookDtoConverter, times(1)).convert(book);
    }

    @Test
    void getBookByIDNotFoundTest() {
        // given
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        // when
        BookDto result = bookService.getBookByID(1L);

        // then
        assertNull(result);
        verify(bookRepository, times(1)).findById(1L);
        verify(bookEntityToBookDtoConverter, never()).convert(any());
    }

    @Test
    void getBooksTest() {
        // given
        Book book = Book.builder().title("Book Title").build();
        BookDto bookDto = BookDto.builder().title("Book Title").build();
        Page<Book> page = new PageImpl<>(List.of(book));
        when(bookRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(bookEntityToBookDtoConverter.convert(book)).thenReturn(bookDto);

        // when
        PagingDto<BookDto> result = bookService.getBooks(0, 10, Sort.Direction.ASC, "title");

        // then
        assertNotNull(result);
        assertEquals(1, result.getValues().size());
        assertEquals("Book Title", result.getValues().get(0).getTitle());
        verify(bookRepository, times(1)).findAll(any(Pageable.class));
        verify(bookEntityToBookDtoConverter, times(1)).convert(book);
    }

    @Test
    void addBookTest() {
        // given
        BookDto bookDto = BookDto.builder().title("Book Title").build();
        Book book = Book.builder().title("Book Title").build();
        when(bookDtoToBookEntityConverter.convert(bookDto)).thenReturn(book);

        // when
        bookService.addBook(bookDto);

        // then
        verify(bookDtoToBookEntityConverter, times(1)).convert(bookDto);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void updateBookTest() {
        // given
        BookDto bookDto = BookDto.builder().title("Updated Title").build();
        Book book = Book.builder().id(1L).title("Old Title").build();
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(bookEntityToBookDtoConverter.convert(book)).thenReturn(bookDto);

        // when
        BookDto result = bookService.updateBook(bookDto, 1L);

        // then
        assertNotNull(result);
        assertEquals("Updated Title", result.getTitle());
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void updateBookNotFoundTest() {
        // given
        BookDto bookDto = BookDto.builder().title("Updated Title").build();
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        // when
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> bookService.updateBook(bookDto, 1L));

        // then
        assertEquals("Book not found", thrown.getMessage());
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void removeBookTest() {
        // when
        bookService.removeBook(1L);

        // then
        verify(bookRepository, times(1)).deleteById(1L);
    }
}

