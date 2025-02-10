package com.example.FrikadasVarias.controller;

import com.example.FrikadasVarias.dto.UserDto;
import com.example.FrikadasVarias.service.EmailService;
import com.example.FrikadasVarias.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class MailController {
    @Autowired
    private UserService userService; // Usamos el servicio de User para acceder a los usuarios

    @Autowired
    private EmailService emailService;

    @GetMapping("/pruebacorreo")
    public String pruebaCorreo() {
        List<UserDto> users = userService.findAllUsers();
        for (UserDto userDto : users) {
            emailService.enviarCorreo(
                    userDto.getEmail(),
                    "Hola a todos los usuarios",
                    "Esto es un mensaje de prueba"
            );
        }
        return "redirect:/";
    }
    //Metodo para enviar la lista de los productos de la cesta por correo
    @PostMapping("/enviaremail")
    public String enviaremail() {

        return "redirect:/cesta";
    }
}