package com.example.FrikadasVarias.service.impl;

import com.example.FrikadasVarias.entity.Cesta;
import com.example.FrikadasVarias.entity.Producto;
import com.example.FrikadasVarias.entity.User;
import com.example.FrikadasVarias.repository.CestaRepository;
import com.example.FrikadasVarias.repository.UserRepository;
import com.example.FrikadasVarias.service.CestaService;
import com.example.FrikadasVarias.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CestaImpl implements CestaService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CestaRepository cestaRepository;

    @Override
    public void addProductoToCesta(String email, Producto producto) {
        // Busca al usuario por su email
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        // Obtén la cesta del usuario (o crea una nueva si no existe)
        Cesta cesta = user.getCesta();
        if (cesta == null) {
            cesta = new Cesta();
            cesta.setUser(user);
            cesta.setProductos(new ArrayList<>()); // Inicializa la lista de productos
            user.setCesta(cesta); // Asocia la nueva cesta al usuario
        }

        // Añade el producto a la lista de productos de la cesta
        List<Producto> productos = cesta.getProductos();
        productos.add(producto);

        // Guarda la cesta actualizada en la base de datos
        cesta.setProductos(productos);
        userRepository.save(user); // Guarda el usuario con la nueva asociación
    }

}
