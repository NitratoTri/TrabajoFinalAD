package com.example.FrikadasVarias.iniciarbbdd;

import com.example.FrikadasVarias.entity.*;
import com.example.FrikadasVarias.repository.*;
import com.example.FrikadasVarias.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CrearBBBDD implements CommandLineRunner{
    @Autowired
    UserRoleRepository userRoleRepository;
    @Autowired
    UserService userService;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    ProductoRepository productoRepository;
    @Autowired
    CategoriaRepository categoriaRepository;
    @Autowired
    MesaRepository mesaRepository;
    @Override
    public void run(String... args) throws Exception {
        if(roleRepository.findAll().isEmpty()){
            Role role = new Role();
            role.setName("ROLE_ADMIN");
            roleRepository.save(role);

            Role role2 = new Role();
            role2.setName("ROLE_USER");
            roleRepository.save(role2);

            User user=new User();
            user.setName("Pablo Alvarez");
            user.setEmail("pabloalvarezolaya@gmail.com");
            user.setPassword("1234");
            userService.saveCifrandoPassword(user);


            //Le asigno rol de ADMIN
            UserRole userRole = new UserRole();
            userRole.setRole(role); //role es el de admin
            userRole.setUser(user);
            userRoleRepository.save(userRole);

            //Creamos categorias de ejemplo
            Categoria categoria1= new Categoria();
            categoria1.setNombre("De Azar");

            Categoria categoria2= new Categoria();
            categoria2.setNombre("Tradicionales");

            Categoria categoria3= new Categoria();
            categoria3.setNombre("Warhammer 40k");

            categoriaRepository.save(categoria1);
            categoriaRepository.save(categoria2);
            categoriaRepository.save(categoria3);

            //Creamos productos de ejemplo
            Producto producto1= new Producto();
            producto1.setNombre("Catan");
            producto1.setPrecio(40.0);
            producto1.setDescripcion("Juego de mesa de estrategia");
            producto1.setImagen("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.eldiario.es%2Ftecnologia%2Fjuegos%2Fcatan-juego-mesa-estrategia_1_1090731.html&psig=AOvVaw3Z9Z6Z9Q6Z9Z6");
            producto1.setCategorias(List.of(categoria2));
            productoRepository.save(producto1);

            Producto producto2= new Producto();
            producto2.setNombre("Risk");
            producto2.setPrecio(30.0);
            producto2.setDescripcion("Juego de mesa de estrategia");
            producto2.setImagen("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.eldiario.es%2Ftecnologia%2Fjuegos%2Fcatan-juego-mesa-estrategia_1_1090731.html&psig=AOvVaw3Z9Z6Z9Q6Z9Z6");
            producto2.setCategorias(List.of(categoria2));
            productoRepository.save(producto2);

            Producto producto3= new Producto();
            producto3.setNombre("Warhammer 40k");
            producto3.setPrecio(100.0);
            producto3.setDescripcion("Juego de mesa de estrategia");
            producto3.setImagen("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.eldiario.es%2Ftecnologia%2Fjuegos%2Fcatan-juego-mesa-estrategia_1_1090731.html&psig=AOvVaw3Z9Z6Z9Q6Z9Z6");
            producto3.setCategorias(List.of(categoria3));
            productoRepository.save(producto3);


            //Creamos Mesas de ejemplo
            Mesa mesa1= new Mesa();
            mesa1.setNumero(1);
            mesa1.setImg("../img/mesas/pequeña.png");
            mesa1.setCapacidad(2);

            Mesa mesa2= new Mesa();
            mesa2.setNumero(2);
            mesa2.setImg("../img/mesas/mediana.png");
            mesa2.setCapacidad(4);

            Mesa mesa3= new Mesa();
            mesa3.setNumero(3);
            mesa3.setImg("../img/mesas/grande.png");
            mesa3.setCapacidad(8);

            //Guardamos las mesas
            mesaRepository.save(mesa1);
            mesaRepository.save(mesa2);
            mesaRepository.save(mesa3);






        }

    }
}
