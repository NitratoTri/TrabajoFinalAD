package com.example.FrikadasVarias.service.impl;

import com.example.FrikadasVarias.dto.UserDto;
import com.example.FrikadasVarias.entity.*;
import com.example.FrikadasVarias.repository.RoleRepository;
import com.example.FrikadasVarias.repository.UserRepository;
import com.example.FrikadasVarias.repository.UserRoleRepository;
import com.example.FrikadasVarias.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

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
        logger.info("Inicio de guardado de usuario con email: {}", userDto.getEmail());

        User user = new User();
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());

        //encrypt the password once we integrate spring security
        //user.setPassword(userDto.getPassword());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
        logger.debug("Usuario guardado en la base de datos");

        //Después de crear el usuario, le asigno el rol usuario
        Role role = roleRepository.findByName("ROLE_USER");
        userRoleRepository.save(new UserRole(user,role));
        logger.info("Rol 'ROLE_USER' asignado al usuario {}", user.getEmail());
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
        String[] name = user.getName().split(" ", 2); // Solo divide en dos partes
        userDto.setId(user.getId());
        userDto.setFirstName(name[0]);
        userDto.setLastName(name.length > 1 ? name[1] : ""); // Si no hay apellido, pon vacío
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
    public List<User> findAllUsersEntity() {
        return userRepository.findAll();
    }

}
