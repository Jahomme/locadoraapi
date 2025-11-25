package io.github.jahomme.locadora.model;

public class Carro {

    private double VALOR_DESCONTO = 50;
    private String modelo;
    private double valorDiaria;

    public Carro(String modelo, double valorDiaria) {
        this.modelo = modelo;
        this.valorDiaria = valorDiaria;
    }

    public double calcularValorAluguel(int dias){
        double valorTotal = dias * valorDiaria;

        if(dias >= 5){
            return (valorTotal) - VALOR_DESCONTO;
        }

        return valorTotal;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public double getValorDiaria() {
        return valorDiaria;
    }

    public void setValorDiaria(double valorDiaria) {
        this.valorDiaria = valorDiaria;
    }
}
