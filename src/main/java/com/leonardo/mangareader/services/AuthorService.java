package com.leonardo.mangareader.services;

import org.springframework.stereotype.Service;

import com.leonardo.mangareader.models.Author;
import com.leonardo.mangareader.repositories.AuthorRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor

@Service
public class AuthorService {
    
    private final AuthorRepository repository;

    public Author create(Author author){
        Author newAuthor;

        if(author.getUrl() != null){
            newAuthor = repository.find(author.getName(), author.getUrl()).orElse(author);
        }else{
            newAuthor = repository.findByNameAndUrlIsNull(author.getName()).orElse(author);
        }

        if(newAuthor.getId() != null)
            return newAuthor;

        return repository.save(newAuthor);
    }

}
