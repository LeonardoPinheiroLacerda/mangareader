package com.leonardo.mangareader.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.leonardo.mangareader.models.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer>{
    
    @Query("SELECT author FROM Author author WHERE author.name = ?1 AND author.url = ?2")
    public Optional<Author> find(String name, String url);

    @Query("SELECT author FROM Author author WHERE author.name = ?1 AND author.url IS NULL")
    public Optional<Author> findByNameAndUrlIsNull(String name);

}
