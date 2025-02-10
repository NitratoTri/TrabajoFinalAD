package com.example.FrikadasVarias.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {
    // Agregar atributos y métodos para el producto
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    /*@Column(columnDefinition = "TEXT")
    private String descripcion;
    */
    //Relation between product and category
    @ManyToMany(mappedBy = "categorias")
    private List<Producto> productos;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
