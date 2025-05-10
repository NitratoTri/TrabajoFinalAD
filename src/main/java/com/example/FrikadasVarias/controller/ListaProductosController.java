package com.example.FrikadasVarias.controller;

import com.example.FrikadasVarias.entity.Categoria;
import com.example.FrikadasVarias.entity.Producto;
import com.example.FrikadasVarias.repository.CategoriaRepository;
import com.example.FrikadasVarias.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class ListaProductosController {
    @Autowired
    private ProductoRepository productoRepo;
    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping("/porcategorias")
    public String filtrarPorCategoria(@RequestParam("categoria") Long categoriaId, Model model) {
        List<Producto> productos = productoRepo.findByCategorias_Id(categoriaId);
        List<Categoria> categorias = categoriaRepository.findAll();
        model.addAttribute("productos", productos);
        model.addAttribute("categorias", categorias);
        return "productos";
    }
}
