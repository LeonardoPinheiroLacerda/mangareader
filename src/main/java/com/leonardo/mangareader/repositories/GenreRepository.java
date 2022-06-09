package com.leonardo.mangareader.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.leonardo.mangareader.models.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer>{
    
    @Query("SELECT genre FROM Genre genre WHERE genre.name = ?1 AND genre.url = ?2")
    public Optional<Genre> find(String name, String url);

}
