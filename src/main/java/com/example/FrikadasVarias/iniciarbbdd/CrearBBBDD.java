package com.example.FrikadasVarias.iniciarbbdd;

import com.example.FrikadasVarias.entity.*;
import com.example.FrikadasVarias.repository.CategoriaRepository;
import com.example.FrikadasVarias.repository.ProductoRepository;
import com.example.FrikadasVarias.repository.RoleRepository;
import com.example.FrikadasVarias.repository.UserRoleRepository;
import com.example.FrikadasVarias.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

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






        }

    }
}
