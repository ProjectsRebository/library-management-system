package com.library.management.system.service.impl;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.library.management.system.converter.PatronDtoToPatronEntityConverter;
import com.library.management.system.converter.PatronEntityToPatronDtoConverter;
import com.library.management.system.dto.PagingDto;
import com.library.management.system.dto.PatronDto;
import com.library.management.system.exception.NotFoundException;
import com.library.management.system.models.Patron;
import com.library.management.system.repository.PatronRepository;
import com.library.management.system.service.PatronService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class PatronServiceImpl implements PatronService {

    private final PatronRepository patronRepository;
    private final PatronEntityToPatronDtoConverter patronEntityToPatronDtoConverter;
    private final PatronDtoToPatronEntityConverter patronDtoToPatronEntityConverter;

    @Override
    @Cacheable(value = "patron", key = "#id")
    public PatronDto getPatronByID(Long id) {
        Optional<Patron> patron = patronRepository.findById(id);
        return patron.map(patronEntityToPatronDtoConverter::convert).orElse(null);
    }

    @Override
    public PagingDto<PatronDto> getPatrons(int pageNumber, int pageSize, Sort.Direction direction, String sortByProperty) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, sortByProperty));
        Page<Patron> page = patronRepository.findAll(pageable);

        if (page.getTotalElements() > 0) {
            return PagingDto.<PatronDto>builder()
                .numberOfPages((long) page.getTotalPages())
                .totalNumberOfElements(page.getTotalElements())
                .currentPage((long) pageNumber)
                .pageSize((long) pageSize)
                .values(page.get()
                    .map(patronEntityToPatronDtoConverter::convert)
                    .collect(Collectors.toList()))
                .build();
        }
        return PagingDto.<PatronDto>builder()
            .values(Collections.emptyList())
            .currentPage(0L)
            .totalNumberOfElements(0L)
            .numberOfPages(0L)
            .fromIndex(0L)
            .totalNumberOfElements(0L)
            .build();
    }

    @Override
    public void addPatron(PatronDto patron) {
        patronRepository.save(patronDtoToPatronEntityConverter.convert(patron));
    }

    @Override
    @CacheEvict(value = "patron", key = "#patronId")
    public PatronDto updatePatron(PatronDto patron, long patronId) {
        Optional<Patron> savedPatron = patronRepository.findById(patronId);
        if (savedPatron.isPresent()) {
            Patron patronEntity = savedPatron.get();
            patronEntity.setName(patron.getName());
            patronEntity.setContactInformation(patron.getContactInformation());
            patronRepository.save(patronEntity);
            return patronEntityToPatronDtoConverter.convert(patronRepository.save(patronEntity));
        }
        throw new NotFoundException("Patron not found");
    }

    @Override
    @Transactional
    @CacheEvict(value = "patron", key = "#patronId")
    public void removePatron(long patronId) {
        patronRepository.deleteById(patronId);
    }
}
