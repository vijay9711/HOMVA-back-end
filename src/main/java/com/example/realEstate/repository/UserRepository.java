package com.example.realEstate.repository;

import com.example.realEstate.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findByEmail(String email);

    boolean existsByEmail(String email);
}
