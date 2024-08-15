package cc.maid.lms.controller;

import cc.maid.lms.dto.CreateBookDTO;
import cc.maid.lms.model.Book;
import cc.maid.lms.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAll();
        if (books.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@Valid @RequestBody CreateBookDTO createBookDTO) {
        Book book = new Book();
        book.setISBN(createBookDTO.ISBN());
        book.setTitle(createBookDTO.title());
        book.setAuthor(createBookDTO.author());
        book.setPublisher(createBookDTO.publisher());
        book.setPublishedDate(createBookDTO.publishedDate());
        book.setPrice(createBookDTO.price());
        book.setGenre(createBookDTO.genre());

        Book createdBook = bookService.add(book);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdBook.getId())
                .toUri();

        return ResponseEntity.created(location).body(createdBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id,
                                           @Valid @RequestBody CreateBookDTO createBookDTO) {
        Book book = bookService.getById(id);
        book.setTitle(createBookDTO.title());
        book.setAuthor(createBookDTO.author());
        book.setPublisher(createBookDTO.publisher());
        book.setPublishedDate(createBookDTO.publishedDate());
        book.setPrice(createBookDTO.price());
        book.setGenre(createBookDTO.genre());

        Book updatedBook = bookService.update(book);

        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        bookService.delete(bookService.getById(id));
        return ResponseEntity.noContent().build();
    }

}
