package io.github.jahomme.locadora.controller;

import io.github.jahomme.locadora.entity.CarroEntity;
import io.github.jahomme.locadora.exceptions.EntityNotFoundException;
import io.github.jahomme.locadora.service.CarroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("carros")
@RequiredArgsConstructor
public class CarroController {

    private final CarroService service;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody CarroEntity carro){
        try {
            var carroSalvo = service.salvar(carro);
            return ResponseEntity.status(HttpStatus.CREATED).body(carroSalvo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(e.getMessage());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> buscarPorId(@PathVariable Long id){

        try {
            var carroEncontrado = service.buscarPorId(id);
            return ResponseEntity.status(HttpStatus.OK).body(carroEncontrado);
        } catch (EntityNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<CarroEntity>> listarTodos() {
        var lista = service.listarTodos();

        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Long id, @RequestBody CarroEntity carro){
        try {
            service.atualizar(id ,carro);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            service.deletar(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
