package com.example.FrikadasVarias.controller;


import com.example.FrikadasVarias.dto.UserDto;
import com.example.FrikadasVarias.entity.User;
import com.example.FrikadasVarias.service.EmailService;
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
public class AuthController {

    private UserService userService;
    @Autowired
    private EmailService emailService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }


    // handler method to handle register user form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto user,
                               BindingResult result,
                               Model model){
        User existing = userService.findByEmail(user.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }
        userService.saveUser(user);
        //Send a confirmation email
        String emailContent = "<h1>Bienvenido a FrikadasVarias!</h1>" +
                "<p>Gracias por registarte y espero que disfrute de la app!</p>" +
                "<p>Username: " + user.getEmail() + "</p>";
        emailService.enviarCorreo(
                user.getEmail(),
                "Bienvenido a Frikadas Varias",
                emailContent
        );
        return "redirect:/register?success";
    }


}
