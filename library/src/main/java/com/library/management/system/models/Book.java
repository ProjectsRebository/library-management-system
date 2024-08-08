package com.library.management.system.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private Integer publicationYear;
    private String isbn;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<BorrowingRecord> borrowingRecords;
}
