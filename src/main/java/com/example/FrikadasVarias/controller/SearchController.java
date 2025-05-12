package com.example.FrikadasVarias.controller;

import com.example.FrikadasVarias.entity.Producto;
import com.example.FrikadasVarias.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/productos")
public class SearchController {
    @Autowired
    private ProductoService productService;

    @GetMapping("/autocomplete")
    public ResponseEntity<List<String>> autocomplete(@RequestParam String query) {
        List<String> nombres = productService.findNamesByQuery(query);
        return ResponseEntity.ok(nombres);
    }

    @GetMapping("/search")
    public ResponseEntity<List<String>> search(@RequestParam String query) {
        List<String> names = productService.findNamesByQuery(query);
        return ResponseEntity.ok(names);
    }

    @GetMapping("/searchalike")
    public String searchAlike(@RequestParam String query, Model model) {
        List<Producto> productos = productService.findByNombreContaining(query);
        model.addAttribute("productos", productos);
        model.addAttribute("query", query); // This adds the query to the model
        return "searchedProduct";
    }


}
