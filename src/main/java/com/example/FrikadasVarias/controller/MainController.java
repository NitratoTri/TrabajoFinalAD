package com.example.FrikadasVarias.controller;

import com.example.FrikadasVarias.dto.UserDto;
import com.example.FrikadasVarias.entity.Comentario;
import com.example.FrikadasVarias.entity.Producto;
import com.example.FrikadasVarias.entity.User;
import com.example.FrikadasVarias.repository.ComentarioRepository;
import com.example.FrikadasVarias.repository.ProductoRepository;

import com.example.FrikadasVarias.service.UserService;

import com.example.FrikadasVarias.service.impl.CestaImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @Autowired
    CestaImpl cestaImpl;
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
    public String cesta(Model model, Authentication auth) {
        // Obtén el email del usuario autenticado
        String email = auth.getName();

        // Busca los productos en la cesta del usuario
        List<Producto> productos = productoRepo.findByCestas_User_Email(email);

        // Calcula el subtotal de los productos en la cesta
        double subtotal = productos.stream().mapToDouble(Producto::getPrecio).sum();

        // Agrega los datos al modelo para pasarlos a la vista
        model.addAttribute("cesta", productos);
        model.addAttribute("subtotal", subtotal);

        return "cesta"; // Nombre del archivo HTML (cesta.html)
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




    // handler method to handle user registration request
    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

//Metodo post de añadir al carrito
@PostMapping("/añadircarrito")
@ResponseBody
public ResponseEntity<String> añadirCarrito(@RequestParam("id") Long id, Authentication auth) {
    String email = auth.getName();
    Producto producto = productoRepo.findById(id).orElse(null);
    if (producto!= null) {
        cestaImpl.addProductoToCesta(email, producto);

        return ResponseEntity.ok("Producto añadido correctamente a la cesta");
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
}

@GetMapping("/perfil")
public String perfil(Model model, Authentication auth) {
    User user = userService.findByEmail(auth.getName());
    model.addAttribute("user", user);
    return "perfil";
}

}
