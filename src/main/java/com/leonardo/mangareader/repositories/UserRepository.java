package com.leonardo.mangareader.repositories;

import java.util.Optional;

import com.leonardo.mangareader.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    
    @Query("SELECT user FROM User user WHERE user.username = ?1")
    public Optional<User> findByUsername(String username);

    @Query("SELECT user FROM User user WHERE user.email = ?1")
    public Optional<User> findByEmail(String email);

}
