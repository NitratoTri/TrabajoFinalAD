package com.example.FrikadasVarias.service;

import com.example.FrikadasVarias.dto.UserDto;
import com.example.FrikadasVarias.entity.Role;
import com.example.FrikadasVarias.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findByEmail(String email);

    List<UserDto> findAllUsers();

    public UserDto convertEntityToDto(User user);
    public void save(User user);

    public List<Role> conseguirRolesByUser(User user);
    public void saveCifrandoPassword(User user);
}
