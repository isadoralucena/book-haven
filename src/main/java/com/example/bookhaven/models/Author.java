package com.example.bookhaven.models;

import com.example.bookhaven.dtos.AuthorDTO;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany(mappedBy = "authors", fetch = FetchType.EAGER)
    private Set<Book> books = new HashSet<>();
    private String name;
    private Integer birthYear;
    private Integer deathYear;

    public Author(){
    }

    public Author(AuthorDTO authorDTO) {
        this.birthYear = authorDTO.birthYear();
        this.name = authorDTO.name();
        this.deathYear = authorDTO.deathYear();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public Integer getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(Integer deathYear) {
        this.deathYear = deathYear;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public String basicToString() {
        return name;
    }

    @Override
    public String toString() {
        String formatedBooks = books.stream()
                .map(Book::basicToString)
                .collect(Collectors.joining(", "));

        return "\nAutor: " + name + '\n' +
                " Ano de nascimento: " + birthYear + '\n' +
                " Ano de falecimento: " + deathYear + '\n' +
                " Livros: " + formatedBooks + '\n';
    }
}

