package com.library.management.system.service;

import org.springframework.data.domain.Sort;

import com.library.management.system.dto.BookDto;
import com.library.management.system.dto.PagingDto;

/**
 * The interface Book service.
 */
public interface BookService {

    /**
     * Gets book by id.
     *
     * @param id the id
     * @return the book by id
     */
    BookDto getBookByID(Long id);

    /**
     * Gets books.
     *
     * @param pageNumber     the page number
     * @param pageSize       the page size
     * @param direction      the direction
     * @param sortByProperty the sort by property
     * @return the books
     */
    PagingDto<BookDto> getBooks(final int pageNumber, final int pageSize, final Sort.Direction direction,
                                final String sortByProperty);

    /**
     * Add book.
     *
     * @param book the book
     */
    void addBook(BookDto book);

    /**
     * Update book book dto.
     *
     * @param book   the book
     * @param bookId the book id
     * @return the book dto
     */
    BookDto updateBook (BookDto book, long bookId);

    /**
     * Remove book.
     *
     * @param bookId the book id
     */
    void removeBook(long bookId);
}
