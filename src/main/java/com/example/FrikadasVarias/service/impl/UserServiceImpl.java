package com.example.FrikadasVarias.service.impl;

import com.example.FrikadasVarias.dto.UserDto;
import com.example.FrikadasVarias.entity.*;
import com.example.FrikadasVarias.repository.RoleRepository;
import com.example.FrikadasVarias.repository.UserRepository;
import com.example.FrikadasVarias.repository.UserRoleRepository;
import com.example.FrikadasVarias.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRoleRepository userRoleRepository;

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());

        //encrypt the password once we integrate spring security
        //user.setPassword(userDto.getPassword());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);

        //Después de crear el usuario, le asigno el rol usuario
        Role role = roleRepository.findByName("ROLE_USER");
        userRoleRepository.save(new UserRole(user,role));
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void saveCifrandoPassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map((user) -> convertEntityToDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto convertEntityToDto(User user){
        UserDto userDto = new UserDto();
        String[] name = user.getName().split(" ");
        userDto.setFirstName(name[0]);
        userDto.setLastName(name[1]);
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        return userDto;
    }
    public List<Role> conseguirRolesByUser(User user){
        List<UserRole> userRoles = userRoleRepository.findByUser(user);
        List<Role> roles = new ArrayList<>();
        for (UserRole userRole : userRoles) {
            roles.add(userRole.getRole());
        }
        return roles;
    }
    @Override
    public void addProductoToCesta(String email, Producto producto) {
        // Busca al usuario por su email
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        // Obtén la cesta del usuario (o crea una nueva si no existe)
        Cesta cesta = user.getCesta();
        if (cesta == null) {
            cesta = new Cesta();
            cesta.setUser(user);
            cesta.setProductos(new ArrayList<>()); // Inicializa la lista de productos
            user.setCesta(cesta); // Asocia la nueva cesta al usuario
        }

        // Añade el producto a la lista de productos de la cesta
        List<Producto> productos = cesta.getProductos();
        productos.add(producto);

        // Guarda la cesta actualizada en la base de datos
        cesta.setProductos(productos);
        userRepository.save(user); // Guarda el usuario con la nueva asociación
    }

}
