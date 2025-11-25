package io.github.jahomme.locadora.model;

import io.github.jahomme.locadora.exceptions.ReservaInvalidaException;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReservaTest {

    Carro carro;
    Cliente cliente;

    @BeforeEach
    void setUp(){
        this.carro = new Carro("Virtus", 50);
        this.cliente = new Cliente("Mario");
    }

    @Test
    public void deveCriarUmaReserva(){
        int dias = 5;
        Reserva reserva = new Reserva(carro, cliente, dias);

        assertThat(reserva).isNotNull();
    }

    @Test
    public void deveRetornarValorTotalDaReserva(){
        int dias = 3;

        Reserva reserva = new Reserva(carro, cliente, dias);

        double valorReserva = reserva.calcularTotalReserva();

        assertEquals(150, valorReserva);

    }

    @Test
    public void deveLancarReservaInvalidaException(){
        int dias = 0;

        assertThrows(ReservaInvalidaException.class, () -> {
            new Reserva(carro, cliente, dias);
        });
    }

}
