package com.example.FrikadasVarias.repository;

import com.example.FrikadasVarias.entity.Cesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CestaRepository extends JpaRepository<Cesta, Long> {
    Cesta findByUserId(Long userId); // Busca cesta por el id del usuario.

}
