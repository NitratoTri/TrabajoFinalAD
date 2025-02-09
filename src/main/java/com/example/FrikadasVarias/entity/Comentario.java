package com.example.FrikadasVarias.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
public class Comentario {
    // Agregar atributos y métodos para el producto
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    private Date date;
    @Column(columnDefinition = "TEXT")
    private String contenido;

    //Relation between comentario and user
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}
