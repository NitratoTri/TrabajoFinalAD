package com.example.FrikadasVarias.service;

import com.example.FrikadasVarias.entity.Producto;
import org.springframework.stereotype.Service;


public interface CestaService {
    void addProductoToCesta(String email, Producto producto);
}
