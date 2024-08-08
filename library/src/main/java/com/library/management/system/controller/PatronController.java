package com.library.management.system.controller;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.library.management.system.dto.PagingDto;
import com.library.management.system.dto.PatronDto;
import com.library.management.system.service.PatronService;
import com.library.management.system.validation.ValidationService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/patrons")
@AllArgsConstructor
public class PatronController {

    private final PatronService patronService;
    private final ValidationService validationService;

    /**
     * Retrieve a list of all patrons.
     *
     * @param pageNumber the page number
     * @param pageSize the page size
     * @param direction the sort direction (e.g., ASC, DESC)
     * @param sortByProperty the property to sort by
     * @return a paginated list of patrons
     */
    @GetMapping
    public ResponseEntity<PagingDto<PatronDto>> getAllPatrons(
        @RequestParam(defaultValue = "0") int pageNumber,
        @RequestParam(defaultValue = "10") int pageSize,
        @RequestParam(defaultValue = "ASC") Sort.Direction direction,
        @RequestParam(defaultValue = "id") String sortByProperty) {
        PagingDto<PatronDto> patrons = patronService.getPatrons(pageNumber, pageSize, direction, sortByProperty);
        return ResponseEntity.ok(patrons);
    }

    /**
     * Retrieve details of a specific patron by ID.
     *
     * @param id the patron ID
     * @return the patron details
     */
    @GetMapping("/{id}")
    public ResponseEntity<PatronDto> getPatronById(@PathVariable Long id) {
        PatronDto patron = patronService.getPatronByID(id);
        if (patron != null) {
            return ResponseEntity.ok(patron);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Add a new patron to the system.
     *
     * @param patronDto the patron information
     * @return response indicating success
     */
    @PostMapping
    public ResponseEntity<Void> addPatron(@RequestBody PatronDto patronDto) {
        validationService.validatePatron(patronDto);
        patronService.addPatron(patronDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Update an existing patron's information.
     *
     * @param id the patron ID
     * @param patronDto the updated patron information
     * @return response indicating success or failure
     */
    @PutMapping("/{id}")
    public ResponseEntity<PatronDto> updatePatron(@PathVariable Long id, @RequestBody PatronDto patronDto) {
        validationService.validatePatron(patronDto);
        PatronDto updatedPatron = patronService.updatePatron(patronDto, id);
        return ResponseEntity.ok(updatedPatron);
    }

    /**
     * Remove a patron from the system.
     *
     * @param id the patron ID
     * @return response indicating success or failure
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removePatron(@PathVariable Long id) {
        patronService.removePatron(id);
        return ResponseEntity.noContent().build();
    }
}
