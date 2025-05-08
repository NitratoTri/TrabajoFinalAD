package com.example.FrikadasVarias.controller;

import com.example.FrikadasVarias.entity.Producto;
import com.example.FrikadasVarias.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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


}
