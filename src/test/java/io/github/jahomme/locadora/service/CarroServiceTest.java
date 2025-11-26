package io.github.jahomme.locadora.service;

import io.github.jahomme.locadora.entity.CarroEntity;
import io.github.jahomme.locadora.exceptions.EntityNotFoundException;
import io.github.jahomme.locadora.repository.CarroRepository;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;
import java.util.Optional;

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
        assertThat(carroSalvo.getModelo()).isEqualTo("Sedan");

        Mockito.verify(repository).save(Mockito.any());
    }

    @Test
    void deveDarErroAoTentarSalvarCarroComDiariaNegativa(){
        CarroEntity carro = new CarroEntity("Sedan", 0, 2026);

        assertThrows(IllegalArgumentException.class, () -> service.salvar(carro));

        Mockito.verify(repository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void deveAtualizarUmCarro(){
        var carroExistente = new CarroEntity("SUV", 80.0, 2026);
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(carroExistente));

        var carroAtualizado = new CarroEntity("Sedan", 100.0, 2025);
        carroAtualizado.setId(1L);
        Mockito.when(repository.save(Mockito.any())).thenReturn(carroAtualizado);

        Long id = 1L;
        var resultado = service.atualizar(id, carroAtualizado);

        assertNotNull(carroAtualizado);
        assertThat(resultado.getAno()).isEqualTo(2025);
        assertThat(resultado.getModelo()).isEqualTo("Sedan");

        Mockito.verify(repository).save(Mockito.any());
    }

    @Test
    void deveDarErroSeTentarAtualizarCarroInexistente(){
        Long id = 1L;
        var carroAtualizado = new CarroEntity("Sedan", 100.0, 2025);

        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.empty());

        var erro = catchThrowable(() -> service.atualizar(id, carroAtualizado));

        assertThat(erro).isInstanceOf(EntityNotFoundException.class);
        Mockito.verify(repository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void deveDeletarUmCarro(){
        Long id = 1L;
        var carro = new CarroEntity("Sedan", 100.0, 2025);

        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(carro));

        service.deletar(id);

        Mockito.verify(repository).deleteById(Mockito.any());

    }

    @Test
    void deveDarErroAoDeletarUmCarroInexistente(){
        Long id = 1L;

        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.empty());

        var erro = catchThrowable(() ->  service.deletar(id));

        assertThat(erro).isInstanceOf(EntityNotFoundException.class);
        Mockito.verify(repository, Mockito.never()).deleteById(Mockito.any());

    }

    @Test
    void deveRetornarUmCarroPorId(){
        Long id = 1L;
        var carro = new CarroEntity("Sedan", 100.0, 2025);

        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(carro));

        var carroEncontrado = service.buscarPorId(id);

        assertThat(carroEncontrado).isNotNull();
        assertThat(carroEncontrado.getModelo()).isEqualTo("Sedan");

        Mockito.verify(repository).findById(Mockito.any());
    }

    @Test
    void deveDarErroAoBuscarCarroInexistente(){
        Long id = 1L;

        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.empty());

        var erro = catchThrowable(() ->  service.buscarPorId(id));

        assertThat(erro).isInstanceOf(EntityNotFoundException.class);

        Mockito.verify(repository).findById(Mockito.any());
    }

    @Test
    void deveRetornarUmaListaDeCarros(){
        var carro = new CarroEntity("Sedan", 100.0, 2025);
        var carro2 = new CarroEntity("Suv", 150.0, 2024);

        Mockito.when(repository.findAll()).thenReturn(List.of(carro, carro2));


        var carros = service.listarTodos();

        assertThat(carros).hasSize(2);
        assertThat(carro).isIn(carros);

        Mockito.verify(repository).findAll();
    }
}