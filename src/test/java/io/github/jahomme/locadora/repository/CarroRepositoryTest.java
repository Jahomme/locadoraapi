package io.github.jahomme.locadora.repository;


import io.github.jahomme.locadora.entity.CarroEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;



@DataJpaTest
@ActiveProfiles("test")
class CarroRepositoryTest {

    CarroEntity carro;

    @Autowired
    private CarroRepository repository;

    @BeforeEach
    void setUp(){
        carro = new CarroEntity("Corolla", 120.0, 2026);
    }

    @Test
    void deveCriarUmCarro(){
        repository.save(carro);

        assertNotNull(carro.getId());
    }

    @Test
    void deveBuscarPorId(){
        repository.save(carro);

        Optional<CarroEntity> carroEncontrado = repository.findById(carro.getId());

        Assertions.assertThat(carroEncontrado).isPresent();
        Assertions.assertThat(carroEncontrado.get().getModelo()).isEqualTo("Corolla");
        Assertions.assertThat(carroEncontrado.get().getValorDiaria()).isEqualTo(120.0);
        Assertions.assertThat(carroEncontrado.get().getAno()).isEqualTo(2026);
    }

    @Test
    void deveAtualizarUmCarro(){
        var carroSalvo = repository.save(carro);

        carroSalvo.setAno(2025);

        var carroAtualizado = repository.save(carroSalvo);

        Assertions.assertThat(carroAtualizado.getAno()).isEqualTo(2025);

    }

    @Test
    void deletarCarro(){
        var carroSalvo = repository.save(carro);

        repository.deleteById(carroSalvo.getId());

        Optional<CarroEntity> carroEncontrado = repository.findById(carroSalvo.getId());

        Assertions.assertThat(carroEncontrado).isEmpty();
    }

    @Test
    @Sql("/sql/popular-carros.sql")
    void deveBuscarCarroPorModelo(){
        List<CarroEntity> lista = repository.findByModelo("SUV");

        CarroEntity carro = lista.stream().findFirst().get();

        assertEquals(1, lista.size());
        Assertions.assertThat(carro.getValorDiaria()).isEqualTo(150.0);
        Assertions.assertThat(carro.getModelo()).isEqualTo("SUV");

    }
}