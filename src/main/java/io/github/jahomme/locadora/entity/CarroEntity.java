package io.github.jahomme.locadora.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "carro")
@Data
public class CarroEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String modelo;
    private double valorDiaria;
    private int ano;

    public CarroEntity(){

    }

    public CarroEntity(String modelo, double valorDiaria, int ano) {
        this.modelo = modelo;
        this.valorDiaria = valorDiaria;
        this.ano = ano;
    }
}
