package com.example.FrikadasVarias.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
public class Mesa {
    // Agregar atributos y métodos para el producto
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer numero;
    private boolean reservado;
    private Date fecha;
    //Relation between user nad mesa
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
