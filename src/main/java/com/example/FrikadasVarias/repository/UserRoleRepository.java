package com.example.FrikadasVarias.repository;


import com.example.FrikadasVarias.entity.User;
import com.example.FrikadasVarias.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    public List<UserRole> findByUser(User user);

}
