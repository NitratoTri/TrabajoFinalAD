package com.example.FrikadasVarias.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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
    //Añadimos el boolean validado
    private boolean validado= true;
    private Date date;
    @Column(columnDefinition = "TEXT")
    private String contenido;

    //Relation between comentario and user
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public boolean isValidado() {
        return validado;
    }

    public void setValidado(boolean validado) {
        this.validado = validado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Comentario{" +
                "id=" + id +
                ", producto=" + producto +
                ", validado=" + validado +
                ", date=" + date +
                ", contenido='" + contenido + '\'' +
                ", user=" + user +
                '}';
    }
}
