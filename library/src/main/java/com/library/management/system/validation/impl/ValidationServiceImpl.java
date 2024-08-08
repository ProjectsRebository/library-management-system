package com.library.management.system.validation.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.library.management.system.dto.BookDto;
import com.library.management.system.dto.PatronDto;
import com.library.management.system.exception.InvalidDataException;
import com.library.management.system.validation.ValidationService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class ValidationServiceImpl implements ValidationService {
    @Override
    public void validateBookDetails(BookDto book) {
        if (book == null || StringUtils.isBlank(book.getIsbn()) || book.getPublicationYear() == null
            || StringUtils.isBlank(book.getTitle()) || StringUtils.isBlank(book.getAuthor())){
           throw new InvalidDataException("isbn, publication year and title shouldn't be null");
        }
    }

    @Override
    public void validatePatron(PatronDto patron) {
        if(StringUtils.isBlank(patron.getName()) || StringUtils.isBlank(patron.getContactInformation())){
        throw new InvalidDataException("patron name and contact information shouldn't be null");
    }
    }
}
