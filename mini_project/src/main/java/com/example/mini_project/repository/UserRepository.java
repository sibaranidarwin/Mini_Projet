package com.example.mini_project.repository;


import com.example.mini_project.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

    @Transactional
    void deleteByUsername(String username);

}