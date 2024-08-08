package com.library.management.system.service.impl;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.library.management.system.converter.BookDtoToBookEntityConverter;
import com.library.management.system.converter.BookEntityToBookDtoConverter;
import com.library.management.system.dto.BookDto;
import com.library.management.system.dto.PagingDto;
import com.library.management.system.exception.NotFoundException;
import com.library.management.system.models.Book;
import com.library.management.system.repository.BookRepository;
import com.library.management.system.service.BookService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookEntityToBookDtoConverter bookEntityToBookDtoConverter;
    private final BookDtoToBookEntityConverter bookDtoToBookEntityConverter;

    @Override
    @Cacheable(value = "book", key = "#id")
    public BookDto getBookByID(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.map(bookEntityToBookDtoConverter::convert).orElse(null);
    }

    @Override
    public PagingDto<BookDto> getBooks(int pageNumber, int pageSize, Sort.Direction direction, String sortByProperty) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, sortByProperty));
        Page<Book> page = bookRepository.findAll(pageable);

        if (page.getTotalElements() > 0) {
            return PagingDto.<BookDto>builder()
                .numberOfPages((long) page.getTotalPages())
                .totalNumberOfElements(page.getTotalElements())
                .currentPage((long) pageNumber)
                .pageSize((long) pageSize)
                .values(page.get()
                    .map(bookEntityToBookDtoConverter::convert)
                    .collect(Collectors.toList()))
                .build();
        }
        return PagingDto.<BookDto>builder()
            .values(Collections.emptyList())
            .currentPage(0L)
            .totalNumberOfElements(0L)
            .numberOfPages(0L)
            .fromIndex(0L)
            .totalNumberOfElements(0L)
            .build();
    }


    @Override
    public void addBook(BookDto book) {
        bookRepository.save(bookDtoToBookEntityConverter.convert(book));
    }

    @Override
    @CacheEvict(value = "book", key = "#bookId")
    public BookDto updateBook(BookDto book, long bookId) {
        Optional<Book> savedBook = bookRepository.findById(bookId);
        if ( savedBook.isPresent()){
            Book book1 = savedBook.get();
            book1.setTitle(book.getTitle());
            book1.setIsbn(book.getIsbn());
            book1.setPublicationYear(book.getPublicationYear());
            return bookEntityToBookDtoConverter.convert(bookRepository.save(book1));
        }
        throw new NotFoundException("Book not found");
    }

    @Override
    @Transactional
    @CacheEvict(value = "book", key = "#bookId")
    public void removeBook(long bookId) {
        bookRepository.deleteById(bookId);
    }
}
