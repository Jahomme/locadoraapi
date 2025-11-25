package io.github.jahomme.locadora.service;

import io.github.jahomme.locadora.entity.CarroEntity;
import io.github.jahomme.locadora.repository.CarroRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CarroServiceTest {

    @InjectMocks
    CarroService service;

    @Mock
    CarroRepository repository;

    @Test
    void deveSalvarUmCarro(){
        CarroEntity carroParaSalvar = new CarroEntity("Sedan", 100.0, 2026);
        CarroEntity carroParaRetornar = new CarroEntity("Sedan", 100.0, 2026);
        carroParaRetornar.setId(1L);

        Mockito.when(repository.save(Mockito.any())).thenReturn(carroParaRetornar);

        var carroSalvo = service.salvar(carroParaSalvar);

        assertNotNull(carroSalvo);
        Assertions.assertThat(carroSalvo.getModelo()).isEqualTo("Sedan");

        Mockito.verify(repository).save(Mockito.any());
    }
}