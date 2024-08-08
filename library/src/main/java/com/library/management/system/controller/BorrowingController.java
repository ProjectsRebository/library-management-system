package com.library.management.system.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.management.system.service.BorrowingService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class BorrowingController {

    private final BorrowingService borrowingService;

    /**
     * Allow a patron to borrow a book.
     *
     * @param bookId the ID of the book
     * @param patronId the ID of the patron
     * @return response indicating success or failure
     */
    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<Void> borrowBook(@PathVariable Long bookId, @PathVariable Long patronId) {
            borrowingService.borrowBook(bookId, patronId);
            return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Record the return of a borrowed book by a patron.
     *
     * @param bookId the ID of the book
     * @param patronId the ID of the patron
     * @return response indicating success or failure
     */
    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<Void> returnBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        borrowingService.returnBook(bookId, patronId);
        return ResponseEntity.noContent().build();
    }
}
