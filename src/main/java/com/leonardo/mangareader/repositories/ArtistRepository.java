package com.leonardo.mangareader.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.leonardo.mangareader.models.Artist;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Integer>{
    
    @Query("SELECT artist FROM Artist artist WHERE artist.name = ?1 AND artist.url = ?2")
    public Optional<Artist> find(String name, String url);

    @Query("SELECT artist FROM Artist artist WHERE artist.name = ?1 AND artist.url IS NULL")
    public Optional<Artist> findByNameAndUrlIsNull(String name);

}
