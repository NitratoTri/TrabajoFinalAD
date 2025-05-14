package com.example.FrikadasVarias.controller;

import com.example.FrikadasVarias.repository.UserRepository;
import com.example.FrikadasVarias.repository.UserRoleRepository;
import com.example.FrikadasVarias.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/admin")
public class UserCrudController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;

    @PostMapping("/deleteuser/{id}")
    public String deleteUser(@PathVariable Long id) {
        // Elimina las relaciones UserRole del usuario
        userRoleRepository.deleteAll(userRoleRepository.findByUser(userRepository.findById(id).orElse(null)));
        // Ahora elimina el usuario
        userRepository.deleteById(id);
        return "redirect:/admin/usuarios";
    }
}
