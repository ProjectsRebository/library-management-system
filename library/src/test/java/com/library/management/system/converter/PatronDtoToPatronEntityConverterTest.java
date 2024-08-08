package com.library.management.system.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.library.management.system.dto.PatronDto;
import com.library.management.system.models.Patron;

class PatronDtoToPatronEntityConverterTest {

    @InjectMocks
    private PatronDtoToPatronEntityConverter converter;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void convert() {
        // Prepare sample data
        PatronDto patronDto = PatronDto.builder()
                .name("Aml Fawzy")
                .contactInformation("aml.fawzy@example.com")
                .build();

        // Convert PatronDto to Patron
        Patron patron = converter.convert(patronDto);

        // Verify conversion
        Assertions.assertNotNull(patron);
        Assertions.assertEquals(patronDto.getName(), patron.getName());
        Assertions.assertEquals(patronDto.getContactInformation(), patron.getContactInformation());
    }
}
