package io.github.jahomme.locadora.repository;


import io.github.jahomme.locadora.entity.CarroEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarroRepository extends JpaRepository<CarroEntity, Long> {

    List<CarroEntity> findByModelo(String modelo);
}
