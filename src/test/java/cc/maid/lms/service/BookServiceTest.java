package cc.maid.lms.service;


import cc.maid.lms.exception.RecordNotFoundException;
import cc.maid.lms.model.Book;
import cc.maid.lms.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book book;


    @BeforeEach
    void setUp() {
        book = Book.builder().id(1L).title("title")
                .ISBN("1231231231")
                .author("author")
                .price(100).build();
    }

    @Test
    @DisplayName("Find all books should return a list of books")
    public void findAll_ShouldReturnListOfBooks() {
        Book anotherBook = Book.builder().id(2L).title("another title")
                .ISBN("4564564564")
                .author("another author")
                .price(150).build();
        when(bookRepository.findAll()).thenReturn(List.of(book, anotherBook));

        List<Book> books = bookService.getAll();

        assertThat(books).hasSize(2).containsExactlyInAnyOrder(book, anotherBook);
    }

    @Test
    @DisplayName("Add book should save and return the book")
    public void add_ShouldSaveAndReturnBook() {
        when(bookRepository.save(book)).thenReturn(book);

        Book savedBook = bookService.add(book);

        assertThat(savedBook).isEqualTo(book);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    @DisplayName("Update book should save and return the updated book")
    public void update_ShouldSaveAndReturnUpdatedBook() {
        Book modifiedBook = Book.builder().id(1L).title("diffTitle")
                .ISBN("1231231231")
                .author("author")
                .price(100).build();
        when(bookRepository.save(modifiedBook)).thenReturn(modifiedBook);

        Book updatedBook = bookService.update(modifiedBook);

        assertThat(updatedBook).isNotEqualTo(book);
        verify(bookRepository, times(1)).save(modifiedBook);
    }

    @Test
    @DisplayName("Delete book should call delete method on repository")
    public void delete_ShouldCallDeleteOnRepository() {
        doNothing().when(bookRepository).delete(book);

        bookService.delete(book);

        verify(bookRepository, times(1)).delete(book);
    }


    @Test
    @DisplayName("FindById should return book when id exists")
    public void findById_ShouldReturnBook_WhenIdExists() {

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Book BookReturn = bookService.getById(1L);

        assertThat(book).isEqualTo(BookReturn);

    }

    @Test
    @DisplayName("Book findById throws exception for wrong id")
    public void findById_ShouldThrowException_WhenIdDoesNotExist() {

        Long nonExistentId = 1L;

        when(bookRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.getById(nonExistentId))
                .isInstanceOf(RecordNotFoundException.class)
                .hasMessageContaining("There is no book with this id: " + nonExistentId);
    }


}
