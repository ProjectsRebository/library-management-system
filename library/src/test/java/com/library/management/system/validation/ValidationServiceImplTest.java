package com.library.management.system.validation;

import com.library.management.system.validation.impl.ValidationServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import com.library.management.system.dto.BookDto;
import com.library.management.system.dto.PatronDto;
import com.library.management.system.exception.InvalidDataException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ValidationServiceImplTest {

    @InjectMocks
    private ValidationServiceImpl validationService;

    @Test
    void validateBookDetailsValidTest() {
        // given
        BookDto validBook = BookDto.builder()
                .isbn("978-3-16-148410-0")
                .title("Effective Java")
                .author("Joshua Bloch")
                .publicationYear(2008)
                .build();

        // when & then
        assertDoesNotThrow(() -> validationService.validateBookDetails(validBook));
    }

    @Test
    void validateBookDetailsInvalidIsbnTest() {
        // given
        BookDto invalidBook = BookDto.builder()
                .isbn("")
                .title("Effective Java")
                .author("Joshua Bloch")
                .publicationYear(2008)
                .build();

        // when & then
        InvalidDataException thrown = assertThrows(InvalidDataException.class, () -> validationService.validateBookDetails(invalidBook));
        assertEquals("isbn, publication year and title shouldn't be null", thrown.getMessage());
    }

    @Test
    void validateBookDetailsInvalidPublicationYearTest() {
        // given
        BookDto invalidBook = BookDto.builder()
                .isbn("978-3-16-148410-0")
                .title("Effective Java")
                .author("Joshua Bloch")
                .publicationYear(null)
                .build();

        // when & then
        InvalidDataException thrown = assertThrows(InvalidDataException.class, () -> validationService.validateBookDetails(invalidBook));
        assertEquals("isbn, publication year and title shouldn't be null", thrown.getMessage());
    }

    @Test
    void validateBookDetailsInvalidTitleTest() {
        // given
        BookDto invalidBook = BookDto.builder()
                .isbn("978-3-16-148410-0")
                .title("")
                .author("Joshua Bloch")
                .publicationYear(2008)
                .build();

        // when & then
        InvalidDataException thrown = assertThrows(InvalidDataException.class, () -> validationService.validateBookDetails(invalidBook));
        assertEquals("isbn, publication year and title shouldn't be null", thrown.getMessage());
    }

    @Test
    void validatePatronValidTest() {
        // given
        PatronDto validPatron = PatronDto.builder()
                .name("John Doe")
                .contactInformation("john.doe@example.com")
                .build();

        // when & then
        assertDoesNotThrow(() -> validationService.validatePatron(validPatron));
    }

    @Test
    void validatePatronInvalidNameTest() {
        // given
        PatronDto invalidPatron = PatronDto.builder()
                .name("")
                .contactInformation("john.doe@example.com")
                .build();

        // when & then
        InvalidDataException thrown = assertThrows(InvalidDataException.class, () -> validationService.validatePatron(invalidPatron));
        assertEquals("patron name and contact information shouldn't be null", thrown.getMessage());
    }

    @Test
    void validatePatronInvalidContactInformationTest() {
        // given
        PatronDto invalidPatron = PatronDto.builder()
                .name("John Doe")
                .contactInformation("")
                .build();

        // when & then
        InvalidDataException thrown = assertThrows(InvalidDataException.class, () -> validationService.validatePatron(invalidPatron));
        assertEquals("patron name and contact information shouldn't be null", thrown.getMessage());
    }
}

