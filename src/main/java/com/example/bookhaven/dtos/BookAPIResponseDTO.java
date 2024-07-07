package com.example.bookhaven.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookAPIResponseDTO(@JsonAlias("results") List<BookDTO> booksDataset) {
}
