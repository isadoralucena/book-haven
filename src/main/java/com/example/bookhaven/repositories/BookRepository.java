package com.example.bookhaven.repositories;

import com.example.bookhaven.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
