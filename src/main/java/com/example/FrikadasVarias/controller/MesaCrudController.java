package com.example.FrikadasVarias.controller;

import com.example.FrikadasVarias.entity.Mesa;
import com.example.FrikadasVarias.entity.Producto;
import com.example.FrikadasVarias.repository.MesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
public class MesaCrudController {
    @Autowired
    MesaRepository mesaRepository;
    private static final String IMAGE_FOLDER =
            System.getenv().getOrDefault("IMAGE_FOLDER", "src/main/resources/static/img/mesas");


    @PostMapping("/crud/eliminarmesa")
    public String borrarMesa(@RequestParam Long id) {
        mesaRepository.deleteById(id);
        return "redirect:/admin/mesascrud";
    }

    @PostMapping("/crud/modificarmesa")
    public String modificarmesa(@RequestParam("id") Long id, Model model) {
        Mesa mesa = mesaRepository.findById(id).orElse(null);
        if (mesa == null) {
            return "redirect:/";
        }
        File carpeta = new File(IMAGE_FOLDER);
        List<String> imagenes = Arrays.stream(Objects.requireNonNull(carpeta.listFiles()))
                .filter(File::isFile)
                .map(File::getName)
                .collect(Collectors.toList());

        model.addAttribute("imagenes", imagenes);
        model.addAttribute("mesa", mesa);

        return "formularioMesas";
    }


    @PostMapping("/crud/guardarmesa")
    public String guardarMesa(@ModelAttribute Mesa mesaForm) {
        if (mesaForm.getId() != null) {
            // Se trata de una actualización: buscamos la mesa existente
            Mesa mesaExistente = mesaRepository.findById(mesaForm.getId()).orElse(null);
            if (mesaExistente != null) {
                // Actualizamos los campos de la mesa existente
                mesaExistente.setNumero(mesaForm.getNumero());
                mesaExistente.setCapacidad(mesaForm.getCapacidad());
                mesaExistente.setFecha(mesaForm.getFecha());
                mesaExistente.setImg(mesaForm.getImg());
                mesaExistente.setReservado(mesaForm.isReservado());
                mesaRepository.save(mesaExistente);
            }
        } else {
            // Inserción: se crea una nueva Mesa
            Mesa nuevaMesa = new Mesa();
            nuevaMesa.setNumero(mesaForm.getNumero());
            nuevaMesa.setCapacidad(mesaForm.getCapacidad());
            nuevaMesa.setFecha(mesaForm.getFecha());
            nuevaMesa.setImg(mesaForm.getImg());
            nuevaMesa.setReservado(mesaForm.isReservado());
            mesaRepository.save(nuevaMesa);
        }
        return "redirect:/admin/mesascrud";
    }

}
