package com.example.FrikadasVarias.repository;

import com.example.FrikadasVarias.entity.Categoria;
import com.example.FrikadasVarias.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

}
