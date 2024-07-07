package com.example.bookhaven.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookDTO(String title, Set<AuthorDTO> authors, @JsonAlias("download_count") Double downloadCount, Set<String> languages) {
}
