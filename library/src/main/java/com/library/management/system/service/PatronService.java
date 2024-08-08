package com.library.management.system.service;

import org.springframework.data.domain.Sort;

import com.library.management.system.dto.PagingDto;
import com.library.management.system.dto.PatronDto;

public interface PatronService {

    /**
     * Gets patron by id.
     *
     * @param id the id
     * @return the patron by id
     */
    PatronDto getPatronByID(Long id);

    /**
     * Gets patrons.
     *
     * @param pageNumber     the page number
     * @param pageSize       the page size
     * @param direction      the direction
     * @param sortByProperty the sort by property
     * @return the patrons
     */
    PagingDto<PatronDto> getPatrons(final int pageNumber, final int pageSize, final Sort.Direction direction,
                                    final String sortByProperty);

    /**
     * Add patron.
     *
     * @param patron the patron
     */
    void addPatron(PatronDto patron);

    /**
     * Update patron patron dto.
     *
     * @param patron the patron
     * @param patronId the patron id
     * @return the patron dto
     */
    PatronDto updatePatron(PatronDto patron, long patronId);

    /**
     * Remove patron.
     *
     * @param patronId the patron id
     */
    void removePatron(long patronId);
}
