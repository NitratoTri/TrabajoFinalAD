package com.example.FrikadasVarias.controller;

import com.example.FrikadasVarias.entity.Mesa;
import com.example.FrikadasVarias.entity.User;
import com.example.FrikadasVarias.repository.MesaRepository;
import com.example.FrikadasVarias.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.List;

@Controller
public class MesaController {
    @Autowired
    private MesaRepository mesaRepository;
    @Autowired
    private UserService userService;

    @PostMapping("/reservarMesa")
    public String reservarMesa(Authentication auth, @RequestParam Date fecha) {
        {
            User user = userService.findByEmail(auth.getName());
            //A la mesa que se le da click en el boton de reservar se le asigna el usuario que ha hecho la reserva
            List<Mesa> mesas = mesaRepository.findAll();
            for (Mesa mesa : mesas) {
                if (mesa.isReservado() == false) {
                    mesa.setReservado(true);
                    mesa.setFecha(fecha);
                    mesa.setUser(user);
                    mesaRepository.save(mesa);
                    userService.save(user);
                    break;
                }

            }
            return "redirect:/reservarmesa";
        }
    }

}