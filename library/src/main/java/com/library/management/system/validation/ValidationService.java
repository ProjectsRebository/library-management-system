package com.library.management.system.validation;

import com.library.management.system.dto.BookDto;
import com.library.management.system.dto.PatronDto;

/**
 * The interface Validation service.
 */
public interface ValidationService {

    /**
     * Validate book details.
     *
     * @param book the book
     */
    void validateBookDetails(BookDto book);

    /**
     * Validate patron.
     *
     * @param patron the patron
     */
    void validatePatron(PatronDto patron);
}
