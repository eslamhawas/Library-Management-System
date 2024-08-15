package cc.maid.lms.service;

import cc.maid.lms.exception.RecordNotFoundException;
import cc.maid.lms.model.Patron;
import cc.maid.lms.repository.PatronRepository;
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
public class PatronServiceTest {

    @Mock
    private PatronRepository patronRepository;

    @InjectMocks
    private PatronService patronService;

    private Patron patron;

    @BeforeEach
    void setUp() {
        patron = Patron.builder()
                .id(1L)
                .name("Eslam")
                .email("Eslam@example.com")
                .phone("123-456-7890")
                .address("123 Main St")
                .build();
    }

    @Test
    @DisplayName("Find all patrons should return a list of patrons")
    public void findAll_ShouldReturnListOfPatrons() {
        Patron anotherPatron = Patron.builder()
                .id(2L)
                .name("Jane Doe")
                .email("jane.doe@example.com")
                .phone("098-765-4321")
                .address("456 Elm St")
                .build();
        when(patronRepository.findAll()).thenReturn(List.of(patron, anotherPatron));

        List<Patron> patrons = patronService.getAll();

        assertThat(patrons).hasSize(2).containsExactlyInAnyOrder(patron, anotherPatron);
    }

    @Test
    @DisplayName("Add patron should save and return the patron")
    public void add_ShouldSaveAndReturnPatron() {
        when(patronRepository.save(patron)).thenReturn(patron);

        Patron savedPatron = patronService.add(patron);

        assertThat(savedPatron).isEqualTo(patron);
        verify(patronRepository, times(1)).save(patron);
    }

    @Test
    @DisplayName("Update patron should save and return the updated patron")
    public void update_ShouldSaveAndReturnUpdatedPatron() {
        Patron updatedPatron = Patron.builder()
                .id(1L)
                .name("John Smith")
                .email("john.smith@example.com")
                .phone("123-456-7890")
                .address("123 Main St")
                .build();
        when(patronRepository.save(updatedPatron)).thenReturn(updatedPatron);

        Patron result = patronService.update(updatedPatron);

        assertThat(result).isNotEqualTo(patron);
        assertThat(result).isEqualTo(updatedPatron);
        verify(patronRepository, times(1)).save(updatedPatron);
    }

    @Test
    @DisplayName("Delete patron should call delete method on repository")
    public void delete_ShouldCallDeleteOnRepository() {
        doNothing().when(patronRepository).delete(patron);

        patronService.delete(patron);

        verify(patronRepository, times(1)).delete(patron);
    }

    @Test
    @DisplayName("FindById should return patron when id exists")
    public void findById_ShouldReturnPatron_WhenIdExists() {
        when(patronRepository.findById(1L)).thenReturn(Optional.of(patron));

        Patron foundPatron = patronService.getById(1L);

        assertThat(foundPatron).isEqualTo(patron);
    }

    @Test
    @DisplayName("FindById should throw exception when id does not exist")
    public void findById_ShouldThrowException_WhenIdDoesNotExist() {
        Long nonExistentId = 2L;
        when(patronRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> patronService.getById(nonExistentId))
                .isInstanceOf(RecordNotFoundException.class)
                .hasMessageContaining("There is no user with this id: " + nonExistentId);
    }
}
