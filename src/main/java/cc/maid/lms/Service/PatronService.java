package cc.maid.lms.Service;

import cc.maid.lms.Exception.RecordNotFoundException;
import cc.maid.lms.Model.Patron;
import cc.maid.lms.Repository.PatronRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PatronService {

    private PatronRepo _patronRepo;

    @Autowired
    public PatronService(PatronRepo patronRepo) {
        _patronRepo = patronRepo;
    }

    public List<Patron> getAll() {
        return _patronRepo.findAll();
    }

    public Patron getById(Long id) {
         return _patronRepo.findById(id).orElseThrow(() ->
                 new RecordNotFoundException("There is no user with this id: " + id));
    }

    @Transactional(rollbackFor = Exception.class)
    public Patron add(Patron patron) {
        return _patronRepo.save(patron);
    }

    @Transactional(rollbackFor = Exception.class)
    public Patron update(Patron patron) {
        return _patronRepo.save(patron);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Patron patron) {
        _patronRepo.delete(patron);
    }



}
