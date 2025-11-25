package io.github.jahomme.locadora.model;

import io.github.jahomme.locadora.exceptions.ReservaInvalidaException;

public class Reserva {
    Carro carro;
    Cliente cliente;
    int dias;

    public Reserva(Carro carro, Cliente cliente, int dias) {

        if(dias < 1){
            throw new ReservaInvalidaException("A reserva deve conter no mínimo 1 dia");
        }

        this.carro = carro;
        this.cliente = cliente;
        this.dias = dias;
    }

    public double calcularTotalReserva(){
        return this.carro.calcularValorAluguel(this.dias);
    }

    public Carro getCarro() {
        return carro;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Integer getDias() {
        return dias;
    }

    public void setDias(Integer dias) {
        this.dias = dias;
    }
}
