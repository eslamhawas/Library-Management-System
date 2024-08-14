package cc.maid.lms.Controller;

import cc.maid.lms.DTO.BookDTO;
import cc.maid.lms.Model.Book;
import cc.maid.lms.Service.BookService;
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
    public ResponseEntity<Book> createBook(@Valid @RequestBody BookDTO bookDTO) {
        Book book = new Book();
        book.setISBN(bookDTO.ISBN());
        book.setTitle(bookDTO.title());
        book.setAuthor(bookDTO.author());
        book.setPublisher(bookDTO.publisher());
        book.setPublishedDate(bookDTO.publishedDate());
        book.setPrice(bookDTO.price());
        book.setGenre(bookDTO.genre());

        Book createdBook = bookService.add(book);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdBook.getId())
                .toUri();

        return ResponseEntity.created(location).body(createdBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id,
                                           @Valid @RequestBody BookDTO bookDTO) {
        Book book = bookService.getById(id);
        book.setTitle(bookDTO.title());
        book.setAuthor(bookDTO.author());
        book.setPublisher(bookDTO.publisher());
        book.setPublishedDate(bookDTO.publishedDate());
        book.setPrice(bookDTO.price());
        book.setGenre(bookDTO.genre());

        Book updatedBook = bookService.update(book);

        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        bookService.delete(bookService.getById(id));
        return ResponseEntity.noContent().build();
    }

}
