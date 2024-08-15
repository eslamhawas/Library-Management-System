package cc.maid.lms.service;

import cc.maid.lms.exception.RecordNotFoundException;
import cc.maid.lms.model.Book;
import cc.maid.lms.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {

    private BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional(readOnly = true)
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "books", key = "#id")
    public Book getById(Long id) {
        return bookRepository.findById(id).orElseThrow(() ->
               new RecordNotFoundException("There is no book with this id: " + id));
    }

    @Transactional(rollbackFor = Exception.class)
    public Book add(Book book) {
        return bookRepository.save(book);
    }

    @Transactional(rollbackFor = Exception.class)
    @CachePut(value = "books", key = "#book.id")
    public Book update(Book book) {
        return bookRepository.save(book);
    }

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "books", key = "#book.id")
    public void delete(Book book) {
        bookRepository.delete(book);
    }



}
