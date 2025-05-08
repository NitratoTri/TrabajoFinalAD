package com.example.FrikadasVarias.iniciarbbdd;

import com.example.FrikadasVarias.entity.*;
import com.example.FrikadasVarias.repository.*;
import com.example.FrikadasVarias.service.UserService;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

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
             final String[] tipos = {"Figura", "Cómic", "Manga", "Póster", "Camiseta", "Juego de mesa", "Miniatura WH40k"};
             final String[] franquicias = {"Spider-Man", "Warhammer 40k", "Dragon Ball", "One Piece", "Batman", "Zelda", "Dark Souls", "Naruto"};


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

            Categoria categoria4= new Categoria();
            categoria4.setNombre("De Cartas");

            Categoria categoria5= new Categoria();
            categoria5.setNombre("De Rol");

            Categoria categoria6= new Categoria();
            categoria6.setNombre("De Estrategia");

            List<Categoria> categorias = new ArrayList<>();
            categorias.add(categoria1);
            categorias.add(categoria2);
            categorias.add(categoria3);
            categorias.add(categoria4);
            categorias.add(categoria5);
            categorias.add(categoria6);

            //Guardamos las categorias
            categoriaRepository.saveAll(categorias);

            //Creamos productos de ejemplo
            Producto producto1= new Producto();
            producto1.setNombre("Catan");
            producto1.setPrecio(40.0);
            producto1.setDescripcion("Juego de mesa de estrategia");
            producto1.setImagen("catan.jpg");
            producto1.setCategorias(List.of(categoria2));
            productoRepository.save(producto1);

            Producto producto2= new Producto();
            producto2.setNombre("Risk");
            producto2.setPrecio(30.0);
            producto2.setDescripcion("Juego de mesa de estrategia");
            producto2.setImagen("risk.jpg");
            producto2.setCategorias(List.of(categoria2));
            productoRepository.save(producto2);

            Producto producto3= new Producto();
            producto3.setNombre("Warhammer 40k");
            producto3.setPrecio(100.0);
            producto3.setDescripcion("Juego de mesa de estrategia");
            producto3.setImagen("whstarter.jpg");
            producto3.setCategorias(List.of(categoria3));
            productoRepository.save(producto3);

            Faker faker = new Faker(new Locale("es"));
            Random random = new Random();
            List<Producto> productos = new ArrayList<>();
            // Creamos 20 productos aleatorios
            List<Categoria> todasLasCategorias = List.of(categoria1, categoria2, categoria3, categoria4, categoria5, categoria6);
            List<String> imagenes = List.of("1.jpg", "2.jpg", "3.jpg", "4.jpg", "5.jpg", "6.jpg");
            for (int i = 0; i < 20; i++) {
                String tipo = tipos[random.nextInt(tipos.length)];
                String franquicia = franquicias[random.nextInt(franquicias.length)];
                String imagen = imagenes.get(random.nextInt(imagenes.size()));

                Producto producto = new Producto();
                producto.setNombre(tipo + " de " + franquicia);
                producto.setDescripcion("Producto friki: " + tipo.toLowerCase() + " inspirado en " + franquicia + ". " + faker.lorem().sentence());

                // Precio con 2 decimales
                BigDecimal precio = BigDecimal.valueOf(10 + random.nextInt(90) + random.nextDouble());
                precio = precio.setScale(2, RoundingMode.HALF_UP);
                producto.setPrecio(precio.doubleValue());



                // ⚠️ Asignar categoría aleatoria
                Categoria categoriaAleatoria = todasLasCategorias.get(random.nextInt(todasLasCategorias.size()));
                producto.setCategorias(List.of(categoriaAleatoria)); // Solo una por simplicidad
                producto.setImagen(imagen);
                // Guardar el producto en la lista
                productos.add(producto);
            }
            // Guardamos los productos en la base de datos
            productoRepository.saveAll(productos);
            System.out.println("🟢 Productos frikis generados y guardados en la base de datos.");


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
