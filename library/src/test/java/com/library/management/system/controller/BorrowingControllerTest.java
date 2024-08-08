package com.library.management.system.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.library.management.system.service.BorrowingService;
import com.fasterxml.jackson.databind.ObjectMapper;

class BorrowingControllerTest {

    @InjectMocks
    private BorrowingController borrowingController;

    @Mock
    private BorrowingService borrowingService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(borrowingController)
            .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
            .build();
    }

    @Test
    void testBorrowBook() throws Exception {
        doNothing().when(borrowingService).borrowBook(1L, 1L);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/borrow/1/patron/1"))
            .andExpect(status().isCreated());

        verify(borrowingService, times(1)).borrowBook(1L, 1L);
    }

    @Test
    void testReturnBook() throws Exception {
        doNothing().when(borrowingService).returnBook(1L, 1L);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/return/1/patron/1"))
            .andExpect(status().isNoContent());

        verify(borrowingService, times(1)).returnBook(1L, 1L);
    }

}
