package com.example.FrikadasVarias.repository;

import com.example.FrikadasVarias.entity.Categoria;
import com.example.FrikadasVarias.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByCestas_User_Email(String email);

    @Query("SELECT p.nombre FROM Producto p WHERE p.nombre LIKE %:query%")
    List<String> findNamesByQuery(String query);

    @Query("SELECT p FROM Producto p WHERE p.nombre LIKE %:query%")
    List<Producto> findByNombreContaining(String query);

    Producto findByNombre(String nombre);

    @Query("SELECT p FROM Producto p JOIN p.categorias c WHERE c.id = :categoriaId")
    List<Producto> findByCategorias_Id(@Param("categoriaId") Long categoriaId);

}
