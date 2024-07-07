package com.example.bookhaven.repositories;

import com.example.bookhaven.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitleContainingIgnoreCase(String name);

    List<Book> findByLanguages(String language);
}
