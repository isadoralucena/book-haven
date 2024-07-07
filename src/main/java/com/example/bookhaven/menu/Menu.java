package com.example.bookhaven.menu;

import com.example.bookhaven.dtos.BookAPIResponseDTO;
import com.example.bookhaven.dtos.BookDTO;
import com.example.bookhaven.models.Author;
import com.example.bookhaven.models.Book;
import com.example.bookhaven.repositories.AuthorRepository;
import com.example.bookhaven.repositories.BookRepository;
import com.example.bookhaven.services.ConsumptionAPIService;
import com.example.bookhaven.services.ConversionDataService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Menu {
    private final Scanner sc = new Scanner(System.in);
    private final ConsumptionAPIService consumption = new ConsumptionAPIService();
    private final ConversionDataService converter = new ConversionDataService();
    private final String ADDRESS = "http://gutendex.com/books";
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    public Menu(){
    }
    public Menu(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public void displayMenu() {
        var opcao = -1;
        while(opcao != 0) {
            var menu = """
                    --------------
                    | BOOK HAVEN |
                    --------------
                   
                    Escolha uma opção:
                    
                    1 - buscar livro pelo título
                    2 - listar livros registrados
                    3 - listar autores registrados
                    4 - listar autores vivos em um determinado ano
                    5 - listar livros em um determinado idioma
                                    
                    0 - Sair                                 
                    """;

            System.out.println(menu);
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    searchForBookByTitle();
                    break;
                case 2:
                    listRegisteredBooks();
                    break;
                case 3:
                    listRegisteredAuthors();
                    break;
                case 4:
                    listAuthorsAliveInOneYear();
                    break;
                case 5:
                    listBooksInOneLanguage ();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void searchForBookByTitle() {
        System.out.println("Digite o título do livro: ");
        var bookName = sc.nextLine();

        Optional<Book> bookOptional = bookRepository.findByTitleContainingIgnoreCase(bookName);
        if(bookOptional.isPresent()){
            System.out.println(bookOptional.get());
        }else{
            try{
                var json = consumption.getData(ADDRESS + "?search=" + bookName.replace(" ", "+").toLowerCase());

                if (json == null || json.trim().isEmpty()) {
                    System.out.println("Nenhum conteúdo encontrado na resposta da API para o título: " + bookName);
                    return;
                }


                BookAPIResponseDTO booksDataset = converter.getData(json, BookAPIResponseDTO.class);

                if (booksDataset.booksDataset().isEmpty()) {
                    System.out.println("Nenhum livro encontrado na API para o título: " + bookName);
                }else {
                        BookDTO bookData = booksDataset.booksDataset().getFirst();

                        System.out.println("Book data: "+bookData);

                        Book book = new Book(bookData);
                        bookRepository.save(book);
                        System.out.println("Livro salvo no banco de dados:");
                        System.out.println(book);
                }
            } catch (Exception e) {
                System.err.println("Erro ao buscar ou processar dados da API: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void listRegisteredBooks() {
        try {
            List<Book> registeredBooks = bookRepository.findAll();
            System.out.println(registeredBooks);
        } catch (Exception e) {
            System.err.println("Erro ao listar os livros registrados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void listRegisteredAuthors() {
        try {
            List<Author> registeredAuthors = authorRepository.findAll();
            System.out.println(registeredAuthors);
        } catch (Exception e) {
            System.err.println("Erro ao listar os autores registrados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void listAuthorsAliveInOneYear() {
        try {
            System.out.println("Deseja ver dados sobre autores vivos em que ano? ");
            var date = sc.nextInt();
            List<Author> authorsAliveInOneYear = authorRepository.findByBirthYearLessThanEqualAndDeathYearGreaterThan(date, date);
            System.out.println(authorsAliveInOneYear);
        } catch (Exception e) {
            System.err.println("Erro ao listar os autores vivos em um ano específico: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void listBooksInOneLanguage() {
        try {
            System.out.println("""
                Insira o idioma para realizar a busca:
                              
                es - espanhol   
                en - inglês
                fr - francês
                pt - português
                """);
            var language = sc.next();
            List<Book> booksInOneLanguage = bookRepository.findByLanguages(language);
            System.out.println(booksInOneLanguage);
        } catch (Exception e) {
            System.err.println("Erro ao listar os livros em um idioma específico: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
