package com.example.FrikadasVarias.repository;


import com.example.FrikadasVarias.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
