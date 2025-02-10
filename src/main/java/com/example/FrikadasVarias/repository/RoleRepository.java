package com.example.FrikadasVarias.repository;

import com.example.FrikadasVarias.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
