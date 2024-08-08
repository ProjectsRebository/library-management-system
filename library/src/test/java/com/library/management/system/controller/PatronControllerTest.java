package com.library.management.system.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.library.management.system.dto.PagingDto;
import com.library.management.system.dto.PatronDto;
import com.library.management.system.service.PatronService;
import com.library.management.system.validation.ValidationService;
import com.fasterxml.jackson.databind.ObjectMapper;

class PatronControllerTest {

    @InjectMocks
    private PatronController patronController;

    @Mock
    private PatronService patronService;

    @Mock
    private ValidationService validationService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(patronController)
            .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
            .build();
    }

    @Test
    void testGetAllPatrons() throws Exception {
        List<PatronDto> patronList = List.of(
            PatronDto.builder().name("Aml Fawzy").contactInformation("aml.fawzy@example.com").build(),
            PatronDto.builder().name("Sara Ahmed").contactInformation("aml.fawzy@example.com").build()
        );

        PagingDto<PatronDto> patrons = PagingDto.<PatronDto>builder()
            .numberOfPages(1L)
            .totalNumberOfElements(2L)
            .currentPage(0L)
            .pageSize(2L)
            .values(patronList)
            .build();
        when(patronService.getPatrons(0, 10, Sort.Direction.ASC, "name")).thenReturn(patrons);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/patrons"))
            .andExpect(status().isOk());
    }

    @Test
    void testGetPatronById() throws Exception {
        PatronDto patronDto = PatronDto.builder()
            .name("Aml")
            .contactInformation("aml.fawzy@example.com")
            .build();

        when(patronService.getPatronByID(1L)).thenReturn(patronDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/patrons/1"))
            .andExpect(status().isOk());

    }

    @Test
    void testAddPatron() throws Exception {
        PatronDto patronDto = PatronDto.builder()
            .name("Aml")
            .build();
        doNothing().when(patronService).addPatron(patronDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/patrons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patronDto)))
            .andExpect(status().isCreated());

    }

    @Test
    void testUpdatePatron() throws Exception {
        PatronDto patronDto = PatronDto.builder()
            .name("Aml")
            .contactInformation("aml.fawzy@example.com")
            .build();
        when(patronService.updatePatron(patronDto, 1L)).thenReturn(patronDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/patrons/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patronDto)))
            .andExpect(status().isOk());
    }

    @Test
    void testDeletePatron() throws Exception {
        doNothing().when(patronService).removePatron(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/patrons/1"))
            .andExpect(status().isNoContent());

        verify(patronService, times(1)).removePatron(1L);
    }
}
