package com.example.FrikadasVarias.controller;

import com.example.FrikadasVarias.dto.UserDto;
import com.example.FrikadasVarias.entity.Categoria;
import com.example.FrikadasVarias.entity.Mesa;
import com.example.FrikadasVarias.entity.Producto;
import com.example.FrikadasVarias.entity.User;
import com.example.FrikadasVarias.repository.CategoriaRepository;
import com.example.FrikadasVarias.repository.MesaRepository;
import com.example.FrikadasVarias.repository.ProductoRepository;
import com.example.FrikadasVarias.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {
    @Autowired
    UserService userService;
    @Autowired
    CategoriaRepository categoriaRepository;
    @Autowired
    ProductoRepository productosrepo;
    @Autowired
    private MesaRepository mesaRepository;
    private static final String IMAGE_FOLDER = "src/main/resources/static/img/mesas"; // Ajusta según tu proyecto

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/usuarios")
    public String listRegisteredUsers(Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/admin/productosCrud")
    public String productosCrud(Model model){
        List<Producto> productos= productosrepo.findAll();
        model.addAttribute("productos", productos);
        return "productoCrud";
    }
    @GetMapping("/admin/formularioproducto")
    public String formularioProducto(Model model, @RequestParam(name = "id", required = false) Long id){

        List<Categoria> categorias= categoriaRepository.findAll();
        model.addAttribute("producto", new Producto());
        model.addAttribute("categorias", categorias);
        return "formularioProducto";
    }
    @GetMapping("/admin/mesascrud")
    public String mesasCrud(Model model){
        List<Mesa> mesas= mesaRepository.findAll();

        model.addAttribute("mesas", mesas);
        return "mesascrud";
    }


    @GetMapping("/admin/insertarmesa")
    public String mesaFormulario( Model model, Authentication auth){
        User user = userService.findByEmail(auth.getName());
        Mesa mesa = new Mesa();
        File carpeta = new File(IMAGE_FOLDER);
        List<String> imagenes = Arrays.stream(carpeta.listFiles())
                .filter(File::isFile)
                .map(File::getName)
                .collect(Collectors.toList());

        model.addAttribute("imagenes", imagenes); // Enviar imágenes a la vista
        model.addAttribute("mesa", mesa);
        model.addAttribute("user", user);
        return "formularioMesas";
    }


}
