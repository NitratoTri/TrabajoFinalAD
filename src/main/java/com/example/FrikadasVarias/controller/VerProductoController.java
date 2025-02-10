package com.example.FrikadasVarias.controller;

import com.example.FrikadasVarias.entity.Comentario;
import com.example.FrikadasVarias.entity.User;
import com.example.FrikadasVarias.repository.ComentarioRepository;
import com.example.FrikadasVarias.repository.ProductoRepository;
import com.example.FrikadasVarias.service.UserService;
import com.example.FrikadasVarias.service.impl.CestaImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class VerProductoController {
    private UserService userService;
    @Autowired
    private ProductoRepository productoRepo;
    @Autowired
    private ComentarioRepository comentarioRepo;
    @Autowired
    CestaImpl cestaImpl;
    @Autowired
    private ComentarioRepository repoComentarios;

    public VerProductoController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/comentar")
    public String comentar(@ModelAttribute Comentario comentario, Model model, Authentication auth) {
        System.out.println(comentario.getContenido());
        System.out.println(comentario.getProducto().getId());
        User user = userService.findByEmail(auth.getName());
        comentario.setUser(user);
        System.out.println(comentario.toString());
        comentarioRepo.save(comentario);
        return "redirect:/verproducto/" + comentario.getProducto().getId();
    }
    @PostMapping("/validarComentario")
    public String cambiarEstado(@RequestParam Long id) {
        try {
            Optional<Comentario> comentarioOpt = repoComentarios.findById(id);
            if (comentarioOpt.isPresent()) {
                Comentario comentario = comentarioOpt.get();
                comentario.setValidado(!comentario.isValidado());
                repoComentarios.save(comentario);
                System.out.println("Comentario actualizado: " + comentario.getId() + ", validado: " + comentario.isValidado());
                return "redirect:/verproducto/" + comentario.getProducto().getId();
            }
            System.out.println("Comentario no encontrado con ID: " + id);
            return "redirect:/";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }
    }

    @PostMapping("/recargarsaldo")
    public String recargarSaldo(@RequestParam Double saldo, Authentication auth) {
        User user = userService.findByEmail(auth.getName());
        user.setSaldo(user.getSaldo() + saldo);
        userService.save(user);
        return "redirect:/perfil";
    }
    @PostMapping("/borrarComentario")
    public String borrarComentario(@RequestParam Long id) {
        try {
            Optional<Comentario> comentarioOpt = repoComentarios.findById(id);
            if (comentarioOpt.isPresent()) {
                Comentario comentario = comentarioOpt.get();
                repoComentarios.delete(comentario);
                System.out.println("Comentario borrado: " + comentario.getId());
                return "redirect:/verproducto/" + comentario.getProducto().getId();
            }
            System.out.println("Comentario no encontrado con ID: " + id);
            return "redirect:/";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }
    }

}
