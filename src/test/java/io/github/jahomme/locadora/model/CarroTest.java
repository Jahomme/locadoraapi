package io.github.jahomme.locadora.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class CarroTest {

    @Test
    public void deveRetornarValorTotalDiarias(){
        Integer dias = 10;
        Carro carro = new Carro("Corolla", 200);

        double valorDiarias = carro.calcularValorAluguel(dias);

        if(dias >= 5){
            Assertions.assertEquals(1950, valorDiarias);
            return;
        }

        Assertions.assertEquals(2000, valorDiarias);
    }
}
