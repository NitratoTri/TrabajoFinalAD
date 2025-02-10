package com.example.FrikadasVarias.service;

import com.example.FrikadasVarias.dto.UserDto;
import com.example.FrikadasVarias.entity.Cesta;
import com.example.FrikadasVarias.entity.Producto;
import com.example.FrikadasVarias.entity.Role;
import com.example.FrikadasVarias.entity.User;
import com.example.FrikadasVarias.repository.CestaRepository;
import com.example.FrikadasVarias.repository.ProductoRepository;
import com.example.FrikadasVarias.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface UserService {


    void saveUser(UserDto userDto);

    User findByEmail(String email);

    List<UserDto> findAllUsers();

    public UserDto convertEntityToDto(User user);
    public void save(User user);

    public List<Role> conseguirRolesByUser(User user);
    public void saveCifrandoPassword(User user);

    void addProductoToCesta(String email, Producto producto);

    //metodo añadir al carrito

}
