package com.example.FrikadasVarias.controller;

import com.example.FrikadasVarias.entity.Categoria;
import com.example.FrikadasVarias.entity.Producto;
import com.example.FrikadasVarias.repository.CategoriaRepository;
import com.example.FrikadasVarias.repository.ProductoRepository;
import com.example.FrikadasVarias.service.FileProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class ProductoCRUDcontroller {
    @Autowired
    ProductoRepository productoRepository;
    @Autowired
    CategoriaRepository repoCategorias;
    @Autowired
    private FileProcessingService service;
    @PostMapping("/crud/insertarp")
    public String insertarProducto(@ModelAttribute Producto producto,
                                   @RequestParam("fichero") MultipartFile fichero,
                                   @RequestParam("categoriasid") List<Long> categoriasid,
                                   Model model) {

        // Asignar las categorías al producto
        List<Categoria> categoriasProducto = repoCategorias.findAllById(categoriasid);
        model.addAttribute("categorias", categoriasProducto);

        // Validar los datos: además de nombre y precio, validamos que se hayan seleccionado categorías
        if (producto.getNombre().isEmpty() || producto.getPrecio() == null || categoriasid == null || categoriasid.isEmpty()) {
            model.addAttribute("error", "Todos los campos son obligatorios.");
            model.addAttribute("producto", producto);
            return "formularioProducto";
        }


        try {
            // Procesar la imagen si se ha subido
            if (!fichero.isEmpty()) {
                String nombreOriginal = fichero.getOriginalFilename();
                String extension = nombreOriginal.substring(nombreOriginal.lastIndexOf(".") + 1);
                String img = "p-" + System.currentTimeMillis() + "-" + nombreOriginal; // Nombre único para evitar conflictos
                String uploadStatus = service.uploadFile(fichero, img);
                if (!"CREATED".equals(uploadStatus)) {
                    model.addAttribute("error", "Error al subir la imagen.");
                    return "formularioProducto";
                }
                producto.setImagen(img); // Guarda el nombre del archivo en la entidad
            }
            // Recuperar las categorías seleccionadas de la base de datos
            List<Categoria> categoriasSeleccionadas = repoCategorias.findAllById(categoriasid);
            producto.setCategorias(categoriasSeleccionadas);
            // Guardar el producto
            productoRepository.save(producto);

            return "redirect:/";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error al guardar el producto.");
            model.addAttribute("producto", producto);
            return "formularioProducto";
        }
    }
    //Meotodo getmapping para modificar un producto
    @PostMapping("/crud/modificarproducto")
    public String modificarProducto(@RequestParam("id") Long id, Model model) {
        Producto producto = productoRepository.findById(id).orElse(null);
        if (producto == null) {
            return "redirect:/";
        }
        model.addAttribute("producto", producto);
        model.addAttribute("categorias", repoCategorias.findAll());
        return "formularioProducto";
    }
    //Metodo para eliminar un producto
    @PostMapping("/crud/eliminarproducto")
    public String eliminarProducto(@RequestParam("id") Long id) {
        productoRepository.deleteById(id);
        return "redirect:/";
    }


}
