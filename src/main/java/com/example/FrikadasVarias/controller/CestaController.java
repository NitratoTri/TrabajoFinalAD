package com.example.FrikadasVarias.controller;

import com.example.FrikadasVarias.entity.Cesta;
import com.example.FrikadasVarias.entity.User;
import com.example.FrikadasVarias.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CestaController {
    @Autowired
    private UserService userService;

    //Metodo post de comprar en el carrito
     @PostMapping("/comprar")
    public String comprar(Authentication auth) {
        System.out.println("Comprando");
         User user = userService.findByEmail(auth.getName());
         Cesta cesta= user.getCesta();
         if (user.getSaldo() < cesta.getPrecioTotal()) {
             return "redirect:/cesta?error";
         }else {
             user.setSaldo(user.getSaldo() - cesta.getPrecioTotal());
             cesta.setPrecioTotal(0);
             cesta.getProductos().clear();
             userService.save(user);

         }



         return "redirect:/cesta";
     }
}
