package com.example.FrikadasVarias.repository;


import com.example.FrikadasVarias.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    Optional<User> findById(Double id);

    void deleteById(Double id);
}
