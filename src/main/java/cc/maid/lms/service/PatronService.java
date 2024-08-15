package cc.maid.lms.service;

import cc.maid.lms.exception.RecordNotFoundException;
import cc.maid.lms.model.Patron;
import cc.maid.lms.repository.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class PatronService {

    private PatronRepository patronRepository;

    @Autowired
    public PatronService(PatronRepository patronRepository) {
        this.patronRepository = patronRepository;
    }

    public List<Patron> getAll() {
        return patronRepository.findAll();
    }

    @Cacheable(value = "patrons", key = "#id")
    public Patron getById(Long id) {
         return patronRepository.findById(id).orElseThrow(() ->
                 new RecordNotFoundException(""+id));
    }

    public Patron add(Patron patron) {
        return patronRepository.save(patron);
    }

    @CachePut(value = "patrons", key = "#patron.id")
    public Patron update(Patron patron) {
        return patronRepository.save(patron);
    }

    @CacheEvict(value = "patrons", key = "#patron.id")
    public void delete(Patron patron) {
        patronRepository.delete(patron);
    }



}
