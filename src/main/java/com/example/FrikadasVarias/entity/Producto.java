package com.example.FrikadasVarias.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Producto {
    // Agregar atributos y métodos para el producto
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    @Column(columnDefinition = "TEXT")
    private String descripcion;
    private Double precio;
    private String imagen;
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Comentario> comentarios;
    //Relation between cesta and product
    @ManyToMany(mappedBy = "productos")
    private List<Cesta> cestas;

    //Relation between productos and categorias
    @ManyToMany
    @JoinTable(
            name = "producto_categoria",
            joinColumns = @JoinColumn(name = "producto_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private List<Categoria> categorias;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public List<Cesta> getCestas() {
        return cestas;
    }

    public void setCestas(List<Cesta> cestas) {
        this.cestas = cestas;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }
}
