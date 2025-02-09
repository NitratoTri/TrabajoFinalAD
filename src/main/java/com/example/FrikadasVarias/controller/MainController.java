package com.example.FrikadasVarias.controller;

import com.example.FrikadasVarias.dto.UserDto;
import com.example.FrikadasVarias.entity.Producto;
import com.example.FrikadasVarias.entity.User;
import com.example.FrikadasVarias.repository.ProductoRepository;
import com.example.FrikadasVarias.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MainController {

    private UserService userService;
    @Autowired
    private ProductoRepository productoRepo;

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



    // handler method to handle user registration request
    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

}
