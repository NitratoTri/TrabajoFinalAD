package com.example.FrikadasVarias.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Cesta {
    // Agregar atributos y métodos para el producto
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Relation between Cesta and user
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    //RElation between Cesta and product
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "cesta_producto",
            joinColumns = @JoinColumn(name = "cesta_id"),
            inverseJoinColumns = @JoinColumn(name = "producto_id"))
    private List<Producto> productos;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
