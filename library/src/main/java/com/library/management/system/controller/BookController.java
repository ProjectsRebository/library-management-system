package com.library.management.system.controller;

import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.library.management.system.dto.BookDto;
import com.library.management.system.dto.PagingDto;
import com.library.management.system.exception.NotFoundException;
import com.library.management.system.service.BookService;
import com.library.management.system.validation.ValidationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;
    private final ValidationService validationService;

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
        BookDto bookDto = bookService.getBookByID(id);
        if (bookDto == null) {
            throw new NotFoundException("Book not found with ID: " + id);
        }
        return ResponseEntity.ok(bookDto);
    }

    @GetMapping
    public ResponseEntity<PagingDto<BookDto>> getBooks(
        @RequestParam (defaultValue = "0") int pageNumber,
        @RequestParam (defaultValue = "10")  int pageSize,
        @RequestParam(defaultValue = "ASC") Sort.Direction direction,
        @RequestParam(defaultValue = "title") String sortByProperty) {
        PagingDto<BookDto> pagingDto = bookService.getBooks(pageNumber, pageSize, direction, sortByProperty);
        return ResponseEntity.ok(pagingDto);
    }

    @PostMapping
    public ResponseEntity<BookDto> addBook(@RequestBody BookDto bookDto) {
        validationService.validateBookDetails(bookDto);
        bookService.addBook(bookDto);
        return ResponseEntity.noContent().build();    }

    @PutMapping("/{bookId}")
    public ResponseEntity<BookDto> updateBook(@PathVariable long bookId, @RequestBody BookDto bookDto) {
        validationService.validateBookDetails(bookDto);
        return ResponseEntity.ok(bookService.updateBook(bookDto, bookId));
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> removeBook(@PathVariable long bookId) {
        bookService.removeBook(bookId);
        return ResponseEntity.noContent().build();
    }
}
