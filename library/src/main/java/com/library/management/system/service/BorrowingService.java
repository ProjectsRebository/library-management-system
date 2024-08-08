package com.library.management.system.service;

public interface BorrowingService {

    /**
     * Allow a patron to borrow a book.
     *
     * @param bookId the ID of the book
     * @param patronId the ID of the patron
     */
    void borrowBook(Long bookId, Long patronId);

    /**
     * Record the return of a borrowed book by a patron.
     *
     * @param bookId the ID of the book
     * @param patronId the ID of the patron
     */
    void returnBook(Long bookId, Long patronId);
}
