package com.example.FrikadasVarias.service;

import com.example.FrikadasVarias.entity.Producto;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductoService {

    @Query("SELECT p FROM Producto p WHERE p.nombre LIKE %:query%")
    public List<String> findNamesByQuery(String query);

    @Query("SELECT p FROM Producto p WHERE p.nombre LIKE %:query%")
    public List<Producto> findByNombreContaining(String query);
}
