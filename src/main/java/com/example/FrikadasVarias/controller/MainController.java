package com.example.FrikadasVarias.controller;

import com.example.FrikadasVarias.dto.UserDto;
import com.example.FrikadasVarias.entity.Comentario;
import com.example.FrikadasVarias.entity.Producto;
import com.example.FrikadasVarias.entity.User;
import com.example.FrikadasVarias.repository.ComentarioRepository;
import com.example.FrikadasVarias.repository.ProductoRepository;

import com.example.FrikadasVarias.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;


import java.sql.Date;
import java.time.LocalDate;

import java.util.List;


@Controller
public class MainController {

    private UserService userService;
    @Autowired
    private ProductoRepository productoRepo;
    @Autowired
   private ComentarioRepository comentarioRepo;

    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"/", "/index"})
    public String home(Model model){
        List<UserDto> users = userService.findAllUsers();
        List<Producto> productos= productoRepo.findAll();

        model.addAttribute("users", users);
        model.addAttribute("productos", productos);
        return "index";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/cesta")
    public String cesta(){
        return "cesta";
    }
    @GetMapping("/admin/logout")
    public String logoutAdmin(){
        return "/logout";
    }

    @PostMapping("/logout")
    public String logout(){
        return "/logout";
    }

    @GetMapping("/listaProductos")
    public String admin(){
        return "/productos";
    }
    @GetMapping("/verproducto/{id}")
    public String verProducto(Model model, @PathVariable Long id) {
        System.out.println("Accediendo al producto con ID: " + id);
        Producto producto = productoRepo.findById(id).orElse(null);
        if (producto == null) {
            System.out.println("Producto no encontrado");
            return "redirect:/"; // Redirigir a una página de error personalizada
        }
        model.addAttribute("producto", producto);
        Comentario comentario = new Comentario();
        comentario.setDate(Date.valueOf(LocalDate.now()));
        comentario.setProducto(producto);
        model.addAttribute("comentario", comentario);
        model.addAttribute("comentarios", comentarioRepo.findByProducto(producto));
        return "verproducto";
    }

    //Comentar method post
    @PostMapping("/comentar")
    public String comentar(@ModelAttribute Comentario comentario, Model model, Authentication auth) {
        System.out.println(comentario.getContenido());
        System.out.println(comentario.getProducto().getId());
        User user = userService.findByEmail(auth.getName());
        comentario.setUser(user);

        comentarioRepo.save(comentario);
        return "redirect:/verproducto/" + comentario.getProducto().getId();
    }

    // handler method to handle user registration request
    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }




}
