package com.example.FrikadasVarias.controller;

import com.example.FrikadasVarias.dto.UserDto;
import com.example.FrikadasVarias.entity.Categoria;
import com.example.FrikadasVarias.entity.Producto;
import com.example.FrikadasVarias.repository.CategoriaRepository;
import com.example.FrikadasVarias.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
@Controller
public class AdminController {

    UserService userService;
    @Autowired
    CategoriaRepository categoriaRepository;

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
    public String productosCrud(){
        return "productoCrud";
    }
    @GetMapping("/admin/formularioproducto")
    public String formularioProducto(Model model){
        List<Categoria> categorias= categoriaRepository.findAll();
        model.addAttribute("producto", new Producto());
        model.addAttribute("categorias", categorias);

        return "formularioProducto";
    }
    @GetMapping("/admin/mesas")
    public String mesasCrud(){
        return "mesascrud";
    }
    @GetMapping("/admin/insertarmesa")
    public String insertarMesa(){
        return "formulariomesa";
    }

}
