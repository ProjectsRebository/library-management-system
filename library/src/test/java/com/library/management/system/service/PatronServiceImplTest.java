package com.library.management.system.service;


import java.util.Collections;
import java.util.Optional;

import com.library.management.system.service.impl.PatronServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.library.management.system.converter.PatronDtoToPatronEntityConverter;
import com.library.management.system.converter.PatronEntityToPatronDtoConverter;
import com.library.management.system.dto.PagingDto;
import com.library.management.system.dto.PatronDto;
import com.library.management.system.exception.NotFoundException;
import com.library.management.system.models.Patron;
import com.library.management.system.repository.PatronRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PatronServiceImplTest {

    @InjectMocks
    private PatronServiceImpl patronService;

    @Mock
    private PatronRepository patronRepository;

    @Mock
    private PatronEntityToPatronDtoConverter patronEntityToPatronDtoConverter;

    @Mock
    private PatronDtoToPatronEntityConverter patronDtoToPatronEntityConverter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getPatronByIDSuccessTest() {
        // given
        Long patronId = 1L;
        Patron patron = Patron.builder().id(patronId).name("John Doe").build();
        PatronDto patronDto = PatronDto.builder().name("John Doe").build();
        when(patronRepository.findById(patronId)).thenReturn(Optional.of(patron));
        when(patronEntityToPatronDtoConverter.convert(patron)).thenReturn(patronDto);

        // when
        PatronDto result = patronService.getPatronByID(patronId);

        // then
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(patronRepository, times(1)).findById(patronId);
        verify(patronEntityToPatronDtoConverter, times(1)).convert(patron);
    }

    @Test
    void getPatronByIDNotFoundTest() {
        // given
        Long patronId = 1L;
        when(patronRepository.findById(patronId)).thenReturn(Optional.empty());

        // when
        PatronDto result = patronService.getPatronByID(patronId);

        // then
        assertNull(result);
        verify(patronRepository, times(1)).findById(patronId);
        verify(patronEntityToPatronDtoConverter, never()).convert(any(Patron.class));
    }

    @Test
    void getPatronsSuccessTest() {
        // given
        int pageNumber = 0;
        int pageSize = 10;
        Sort.Direction direction = Sort.Direction.ASC;
        String sortByProperty = "name";
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, sortByProperty));
        Patron patron = Patron.builder().id(1L).name("John Doe").build();
        PatronDto patronDto = PatronDto.builder().name("John Doe").build();
        Page<Patron> page = new PageImpl<>(Collections.singletonList(patron), pageable, 1);
        when(patronRepository.findAll(pageable)).thenReturn(page);
        when(patronEntityToPatronDtoConverter.convert(patron)).thenReturn(patronDto);

        // when
        PagingDto<PatronDto> result = patronService.getPatrons(pageNumber, pageSize, direction, sortByProperty);

        // then
        assertNotNull(result);
        assertEquals(1L, result.getNumberOfPages());
        assertEquals(1L, result.getTotalNumberOfElements());
        assertEquals(0L, result.getCurrentPage());
        assertEquals(10L, result.getPageSize());
        assertEquals(1, result.getValues().size());
        assertEquals("John Doe", result.getValues().get(0).getName());
        verify(patronRepository, times(1)).findAll(pageable);
        verify(patronEntityToPatronDtoConverter, times(1)).convert(patron);
    }

    @Test
    void getPatronsEmptyResultTest() {
        // given
        int pageNumber = 0;
        int pageSize = 10;
        Sort.Direction direction = Sort.Direction.ASC;
        String sortByProperty = "name";
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, sortByProperty));
        Page<Patron> page = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(patronRepository.findAll(pageable)).thenReturn(page);

        // when
        PagingDto<PatronDto> result = patronService.getPatrons(pageNumber, pageSize, direction, sortByProperty);

        // then
        assertNotNull(result);
        assertEquals(0L, result.getNumberOfPages());
        assertEquals(0L, result.getTotalNumberOfElements());
        assertEquals(0L, result.getCurrentPage());
        assertTrue(result.getValues().isEmpty());
        verify(patronRepository, times(1)).findAll(pageable);
        verify(patronEntityToPatronDtoConverter, never()).convert(any(Patron.class));
    }

    @Test
    void addPatronTest() {
        // given
        PatronDto patronDto = PatronDto.builder().name("John Doe").build();
        Patron patron = Patron.builder().name("John Doe").build();
        when(patronDtoToPatronEntityConverter.convert(patronDto)).thenReturn(patron);

        // when
        patronService.addPatron(patronDto);

        // then
        verify(patronDtoToPatronEntityConverter, times(1)).convert(patronDto);
        verify(patronRepository, times(1)).save(patron);
    }

    @Test
    void updatePatronSuccessTest() {
        // given
        Long patronId = 1L;
        PatronDto patronDto = PatronDto.builder().name("John Doe Updated").contactInformation("Updated Info").build();
        Patron patron = Patron.builder().id(patronId).name("John Doe").contactInformation("Old Info").build();
        when(patronRepository.findById(patronId)).thenReturn(Optional.of(patron));
        when(patronRepository.save(Mockito.any())).thenReturn(patron);
        when(patronEntityToPatronDtoConverter.convert(any(Patron.class))).thenReturn(patronDto);

        // when
        PatronDto result = patronService.updatePatron(patronDto, patronId);

        // then
        assertNotNull(result);
        assertEquals("John Doe Updated", result.getName());
        assertEquals("Updated Info", result.getContactInformation());
        verify(patronRepository, times(2)).save(patron); // save is called twice
        verify(patronEntityToPatronDtoConverter, times(1)).convert(patron);
    }

    @Test
    void updatePatronNotFoundTest() {
        // given
        Long patronId = 1L;
        PatronDto patronDto = PatronDto.builder().name("John Doe Updated").build();
        when(patronRepository.findById(patronId)).thenReturn(Optional.empty());

        // when
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> patronService.updatePatron(patronDto, patronId));

        // then
        assertEquals("Patron not found", thrown.getMessage());
        verify(patronRepository, times(1)).findById(patronId);
        verify(patronRepository, never()).save(any(Patron.class));
        verify(patronEntityToPatronDtoConverter, never()).convert(any(Patron.class));
    }

    @Test
    void removePatronTest() {
        // given
        long patronId = 1L;

        // when
        patronService.removePatron(patronId);

        // then
        verify(patronRepository, times(1)).deleteById(patronId);
    }
}
