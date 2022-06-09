package com.leonardo.mangareader.services;

import org.springframework.stereotype.Service;

import com.leonardo.mangareader.models.Artist;
import com.leonardo.mangareader.repositories.ArtistRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor

@Service
public class ArtistService {
    
    private final ArtistRepository repository;

    public Artist create(Artist artist){
        Artist newArtist;

        if(artist.getUrl() != null){
            newArtist = repository.find(artist.getName(), artist.getUrl()).orElse(artist);
        }else{
            newArtist = repository.findByNameAndUrlIsNull(artist.getName()).orElse(artist);
        }

        if(newArtist.getId() != null)
            return newArtist;

        return repository.save(newArtist);
    }

}
