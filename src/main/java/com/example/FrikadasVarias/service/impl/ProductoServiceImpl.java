package com.example.FrikadasVarias.service.impl;


import com.example.FrikadasVarias.entity.Producto;
import com.example.FrikadasVarias.repository.ProductoRepository;
import com.example.FrikadasVarias.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductoServiceImpl implements ProductoService {
    @Autowired
    private ProductoRepository productRepository; // Asegúrate de tener un repositorio para acceder a la base de datos

    @Override
    public List<String> findNamesByQuery(String query) {
        return productRepository.findNamesByQuery(query); // Cambia esto por la lista de nombres encontrados
    }
    @Override
    public List<Producto> findByNombreContaining(String query) {
        return productRepository.findByNombreContaining(query); // Cambia esto por la lista de productos encontrados
    }

}
