package com.library.management.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.management.system.models.Book;

public interface BookRepository extends JpaRepository<Book,Long> {
}
