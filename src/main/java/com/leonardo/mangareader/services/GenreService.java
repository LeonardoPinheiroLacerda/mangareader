package com.leonardo.mangareader.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.leonardo.mangareader.models.Genre;
import com.leonardo.mangareader.repositories.GenreRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor

@Service
public class GenreService {
    
    private final GenreRepository repository;

    public Genre create(Genre genre){
        Genre newGenre = repository.find(genre.getName(), genre.getUrl()).orElse(genre);

        if(newGenre.getId() != null)
            return newGenre;

        return repository.save(genre);
    }

    public Set<Genre> createSet(Set<Genre> genres){
        Set<Genre> newGenres = new HashSet<>();

        for(Genre genre : genres){
            genres.add(create(genre));
        }

        return newGenres;
    }

}
