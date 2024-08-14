package cc.maid.lms.Service;

import cc.maid.lms.Exception.RecordNotFoundException;
import cc.maid.lms.Model.Book;
import cc.maid.lms.Repository.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {

    private BookRepo _bookRepo;

    @Autowired
    public BookService(BookRepo bookRepo) {
        _bookRepo = bookRepo;
    }

    public List<Book> getAll() {
        return _bookRepo.findAll();
    }

    public Book getById(Long id) {
        return _bookRepo.findById(id).orElseThrow(() ->
               new RecordNotFoundException("There is no book with this id: " + id));
    }

    @Transactional(rollbackFor = Exception.class)
    public Book add(Book book) {
        return _bookRepo.save(book);
    }

    @Transactional(rollbackFor = Exception.class)
    public Book update(Book book) {
        return _bookRepo.save(book);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Book book) {
        _bookRepo.delete(book);
    }
}
