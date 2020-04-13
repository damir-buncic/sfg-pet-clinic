package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {

    @Mock
    OwnerRepository ownerRepository;

    @InjectMocks
    OwnerSDJpaService service;

    Owner owner;

    @BeforeEach
    void setUp() {
        owner = Owner.builder().id(1L).lastName("Smith").build();
    }

    @Test
    void findByLastName() {
        when(ownerRepository.findByLastName(any())).thenReturn(owner);
        Owner smith = service.findByLastName("Smith");
        assertNotNull(smith);
        assertEquals(owner.getId(), smith.getId());

        verify(ownerRepository).findByLastName(any());
    }

    @Test
    void findAll() {
        HashSet<Owner> owners = new HashSet<>();
        owners.add(owner);
        owners.add(Owner.builder().id(2L).build());

        when(ownerRepository.findAll()).thenReturn(owners);

        Set<Owner> result = service.findAll();

        assertEquals(2, result.size());
        verify(ownerRepository).findAll();
    }

    @Test
    void findById() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(owner));
        Owner result = service.findById(1L);

        assertNotNull(result);
    }

    @Test
    void findByIdNotFound() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.empty());
        Owner result = service.findById(1L);

        assertNull(result);
    }

    @Test
    void save() {
        when(ownerRepository.save(any())).thenReturn(owner);
        Owner result = service.save(new Owner());

        assertNotNull(result);

    }

    @Test
    void delete() {
        service.delete(new Owner());
        verify(ownerRepository).delete(any());
    }

    @Test
    void deleteById() {
        service.deleteById(1L);
        verify(ownerRepository).deleteById(any());
    }
}