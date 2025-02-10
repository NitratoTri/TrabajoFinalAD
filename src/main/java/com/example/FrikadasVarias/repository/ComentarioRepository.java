package com.example.FrikadasVarias.repository;

import com.example.FrikadasVarias.entity.Comentario;
import com.example.FrikadasVarias.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    List<Comentario> findByProductoId(Long id);

    public List<Comentario> findByProducto(Producto producto);
}
