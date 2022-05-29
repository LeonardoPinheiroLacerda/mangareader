package com.leonardo.mangareader.repositories;

import java.util.Optional;

import com.leonardo.mangareader.models.Manga;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MangaRepository extends JpaRepository<Manga, Integer>{
    
    @Query("SELECT manga FROM Manga manga WHERE manga.url = ?1")
    public Optional<Manga> findByUrl(String url);

}
