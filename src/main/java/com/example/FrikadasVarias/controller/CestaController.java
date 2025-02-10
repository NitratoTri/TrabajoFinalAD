package com.example.FrikadasVarias.controller;

import com.example.FrikadasVarias.entity.Cesta;
import com.example.FrikadasVarias.entity.Producto;
import com.example.FrikadasVarias.entity.User;
import com.example.FrikadasVarias.service.EmailService;
import com.example.FrikadasVarias.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CestaController {
    @Autowired
    private EmailService emailService;
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

             StringBuilder emailContent = new StringBuilder();
             emailContent.append("<h1>Compra Exitosa</h1>");
             emailContent.append("<p>Gracias por su compra. Los productos adquiridos son:</p>");
             emailContent.append("<ul>");

             for (Producto p : cesta.getProductos()) {
                 emailContent.append("<li>")
                         .append(p.getNombre())
                         .append(" - ")
                         .append(p.getPrecio())
                         .append(" €</li>");
             }

             emailContent.append("</ul>");


             user.setSaldo(user.getSaldo() - cesta.getPrecioTotal());
             cesta.setPrecioTotal(0);
             cesta.getProductos().clear();
             userService.save(user);

             // Enviar el correo al usuario
             emailService.enviarCorreo(
                     user.getEmail(),
                     "Compra Exitosa",
                     emailContent.toString()
             );

         }



         return "redirect:/cesta";
     }
}
