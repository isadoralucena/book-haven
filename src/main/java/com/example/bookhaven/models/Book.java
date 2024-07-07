package com.example.bookhaven.models;

import com.example.bookhaven.dtos.BookDTO;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Double downloadCount;
    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "language")
    private Set<String> languages = new HashSet<>();
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "books_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors = new HashSet<>();

    public Book(){

    }

    public Book(BookDTO bookData) {
        this.languages.addAll(bookData.languages());
        System.out.println("This.authors" + this.authors);
        bookData.authors().forEach(authorDTO -> this.addAuthor(new Author(authorDTO)));
        this.downloadCount = bookData.downloadCount();
        this.title = bookData.title();
    }

    public void addAuthor(Author author) {
        this.authors.add(author);
        author.getBooks().add(this);
    }

    public void removeAuthor(Author author) {
        this.authors.remove(author);
        author.getBooks().remove(this);
    }

    public Set<String> getLanguages() {
        return languages;
    }

    public void setLanguages(Set<String> languages) {
        this.languages = languages;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Double downloadCount) {
        this.downloadCount = downloadCount;
    }

    public String basicToString() {
        return title;
    }

    @Override
    public String toString() {
        String formatedAuthors = authors.stream()
                .map(Author::basicToString)
                .collect(Collectors.joining(", "));

        return "\nLivro: " + title + '\n' +
                " Número de downloads: " + downloadCount + '\n' +
                " Idioma(s): " + languages + '\n' +
                " Autor(es):" + formatedAuthors + '\n';
    }
}
