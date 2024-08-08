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

import com.library.management.system.dto.BookDto;
import com.library.management.system.dto.PagingDto;
import com.library.management.system.service.BookService;
import com.library.management.system.validation.ValidationService;
import com.fasterxml.jackson.databind.ObjectMapper;

class BookControllerTest {

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;

    @Mock
    private ValidationService validationService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(bookController)
            .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
            .build();
    }

    @Test
    void testGetAllBooks() throws Exception {
        PagingDto<BookDto> pagingDto = PagingDto.<BookDto>builder()
            .numberOfPages(1L)
            .totalNumberOfElements(1L)
            .currentPage(0L)
            .pageSize(10L)
            .values(List.of(BookDto.builder().build()))
            .build();
        when(bookService.getBooks(0, 10, Sort.Direction.ASC, "title")).thenReturn(pagingDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books")
                .param("page", "0")
                .param("size", "10")
                .param("sortBy", "title")
                .param("direction", "ASC"))
            .andExpect(status().isOk());

        verify(bookService, times(1)).getBooks(0, 10, Sort.Direction.ASC, "title");
    }

    @Test
    void testGetBookById() throws Exception {
        BookDto bookDto = BookDto.builder()
            .id(1L)
            .title("Test Book")
            .isbn("123456789")
            .publicationYear(2022)
            .build();
        when(bookService.getBookByID(1L)).thenReturn(bookDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.title").value("Test Book"));

        verify(bookService, times(1)).getBookByID(1L);
    }

    @Test
    void testAddBook() throws Exception {
        BookDto bookDto = BookDto.builder()
            .title("New Book")
            .isbn("987654321")
            .publicationYear(2023)
            .build();
        doNothing().when(bookService).addBook(bookDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookDto)))
            .andExpect(status().isNoContent());
    }

    @Test
    void testUpdateBook() throws Exception {
        BookDto bookDto = BookDto.builder()
            .id(1L)
            .title("Updated Book")
            .isbn("123456789")
            .publicationYear(2023)
            .build();
        when(bookService.updateBook(bookDto, 1L)).thenReturn(bookDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookDto)))
            .andExpect(status().isOk());

    }

    @Test
    void testDeleteBook() throws Exception {
        doNothing().when(bookService).removeBook(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/1"))
            .andExpect(status().isNoContent());

        verify(bookService, times(1)).removeBook(1L);
    }
}
