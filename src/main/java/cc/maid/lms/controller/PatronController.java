package cc.maid.lms.controller;

import cc.maid.lms.dto.PatronDTO;
import cc.maid.lms.model.Patron;
import cc.maid.lms.service.PatronService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/patrons")
public class PatronController {

    private PatronService patronService;

    @Autowired
    public PatronController(PatronService patronService) {
        this.patronService = patronService;
    }

    @GetMapping
    public ResponseEntity<List<Patron>> getAllPatrons() {
        List<Patron> patrons = patronService.getAll();
        if (patrons.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(patrons);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patron> getPatronById(@PathVariable Long id) {
        return ResponseEntity.ok(patronService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Patron> createPatron(@Valid @RequestBody PatronDTO patronDTO) {
        Patron patron = new Patron();
        patron.setName(patronDTO.name());
        patron.setPhone(patronDTO.phone());
        patron.setEmail(patronDTO.email());
        patron.setAddress(patronDTO.address());

        Patron createdPatron = patronService.add(patron);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdPatron.getId())
                .toUri();

        return ResponseEntity.created(location).body(createdPatron);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patron> updatePatron (@PathVariable Long id,
                                                @Valid @RequestBody PatronDTO patronDTO) {
        Patron patron = patronService.getById(id);
        patron.setName(patronDTO.name());
        patron.setPhone(patronDTO.phone());
        patron.setEmail(patronDTO.email());
        patron.setAddress(patronDTO.address());

        Patron updatedPatron = patronService.update(patron);

        return ResponseEntity.ok(updatedPatron);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Patron> deletePatron (@PathVariable Long id) {
        patronService.delete(patronService.getById(id));
        return ResponseEntity.noContent().build();
    }
}
