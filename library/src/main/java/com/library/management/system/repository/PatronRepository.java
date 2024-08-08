package com.library.management.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.management.system.models.Patron;

public interface PatronRepository extends JpaRepository<Patron,Long> {
}
