package com.example.FrikadasVarias.controller;

import com.example.FrikadasVarias.entity.User;
import com.example.FrikadasVarias.service.FileProcessingService;
import com.example.FrikadasVarias.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class PerfilController {
    @Autowired
    private UserService userService;
    @Autowired
    private FileProcessingService service;
    @GetMapping("/perfil")
    public String perfil(Model model, Authentication auth) {
        try {
            User user = userService.findByEmail(auth.getName());
            model.addAttribute("user", user);
            return "perfil";
        } catch (Exception e) {
            return "redirect:/";
        }

    }
    @GetMapping("/perfil/editar")
    public String editarPerfil(Model model, Authentication auth) {
        try {
            User user = userService.findByEmail(auth.getName());
            model.addAttribute("user", user);
            return "editarPerfil";
        } catch (Exception e) {
            return "redirect:/";
        }
    }
@PostMapping("/editarPerfil")
public String editarPerfilPost(Model model, Authentication auth, User user, @RequestParam("fichero") MultipartFile fichero) {
    try {
        User user1 = userService.findByEmail(auth.getName());
        if (user1 == null) {
            model.addAttribute("error", "User not found.");
            return "redirect:/perfil";
        }

        // Update user details
        user1.setName(user.getName());
        user1.setEmail(user.getEmail());

        // Process the uploaded file if it exists
        if (fichero != null && !fichero.isEmpty()) {
            String nombreOriginal = fichero.getOriginalFilename();
            String img = "p-" + System.currentTimeMillis() + "-" + nombreOriginal; // Unique name to avoid conflicts
            String uploadStatus = service.uploadFile(fichero, img);
            if (!"CREATED".equals(uploadStatus)) {
                model.addAttribute("error", "Error uploading the image.");
                return "perfil";
            }
            user1.setProfilepicture(img); // Update the profile picture field
        }

        // Update password only if provided
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user1.setPassword(user.getPassword());
        }

        userService.save(user1);
        return "redirect:/perfil";
    } catch (Exception e) {
        model.addAttribute("error", "An unexpected error occurred.");
        return "redirect:/perfil";
    }
}
   @PostMapping("/perfil/cambiarpfp")
    public String cambiarPfp(Model model, Authentication auth, @RequestParam("fichero") MultipartFile fichero) {
        try {
            User user1 = userService.findByEmail(auth.getName());
            // Procesar la imagen si se ha subido
            if (!fichero.isEmpty()) {
                String nombreOriginal = fichero.getOriginalFilename();
                String img = "p-" + System.currentTimeMillis() + "-" + nombreOriginal; // Nombre único para evitar conflictos
                String uploadStatus = service.uploadFile(fichero, img);
                if (!"CREATED".equals(uploadStatus)) {
                    model.addAttribute("error", "Error al subir la imagen.");
                    return "/perfil";
                }
                user1.setProfilepicture(img); // Actualiza el campo profilepicture en user1
            }
            userService.save(user1); // Guarda los cambios en la base de datos
            return "redirect:/perfil";
        } catch (Exception e) {
            return "redirect:/";
        }
    }
}
