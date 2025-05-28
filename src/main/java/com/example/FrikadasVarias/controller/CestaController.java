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

    // Método POST para comprar en el carrito
    @PostMapping("/comprar")
    public String comprar(Authentication auth) {
        User user = userService.findByEmail(auth.getName());
        Cesta cesta = user.getCesta();

        if (user.getSaldo() < cesta.getPrecioTotal()) {
            return "redirect:/cesta?error";
        }

        // Construir el contenido del correo electrónico
        StringBuilder emailContent = new StringBuilder();
        emailContent.append("<h1>Compra Exitosa</h1>")
                    .append("<p>Gracias por su compra. Los productos adquiridos son:</p>")
                    .append("<ul>");

        cesta.getProductos().forEach(p -> emailContent.append("<li>")
                .append(p.getNombre())
                .append(" - ")
                .append(String.format("%.2f", p.getPrecio()))
                .append(" $</li>")
        );

        emailContent.append("</ul>")
                    .append("<p>Total: ")
                    .append(String.format("%.2f", cesta.getPrecioTotal()))
                    .append(" $</p>");

        // Actualizar saldo y limpiar la cesta
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

        return "redirect:/cesta";
    }
    // Método POST para quitar un producto a la cesta
    @PostMapping("/quitarProducto")
    public String quitarProducto(Authentication auth, Long productoId) {
        User user = userService.findByEmail(auth.getName());
        Cesta cesta = user.getCesta();
        Producto producto = cesta.getProductos().stream()
                .filter(p -> p.getId().equals(productoId))
                .findFirst()
                .orElse(null);

        if (producto != null) {
            cesta.getProductos().remove(producto);
            cesta.setPrecioTotal(cesta.getPrecioTotal() - producto.getPrecio());
            userService.save(user);
        }

        return "redirect:/cesta";
    }

}