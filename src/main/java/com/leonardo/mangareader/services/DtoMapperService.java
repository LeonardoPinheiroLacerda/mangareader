package com.leonardo.mangareader.services;

import com.leonardo.mangareader.dtos.SimpleMangaDTO;
import com.leonardo.mangareader.dtos.UserDTO;
import com.leonardo.mangareader.models.Manga;
import com.leonardo.mangareader.models.User;

import org.springframework.stereotype.Service;

@Service
public class DtoMapperService {
    
    public UserDTO userToUserDTO(User user){
        return new UserDTO(
            user.getId(), 
            user.getUsername(), 
            user.getEmail()
        );
    }

    public SimpleMangaDTO mangaTSimpleMangaDTO(Manga manga, String apiUrlPrefix){
        return new SimpleMangaDTO(
            manga.getName(), 
            manga.getCover(), 
            manga.getUrl(), 
            manga.getLastRead(),
            apiUrlPrefix + manga.getUrl()
        );
    }

}
