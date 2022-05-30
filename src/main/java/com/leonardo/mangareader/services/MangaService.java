package com.leonardo.mangareader.services;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import com.leonardo.mangareader.MangareaderApplication;
import com.leonardo.mangareader.dtos.MangaDTO;
import com.leonardo.mangareader.dtos.SimpleMangaDTO;
import com.leonardo.mangareader.models.Manga;
import com.leonardo.mangareader.models.User;
import com.leonardo.mangareader.repositories.MangaRepository;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor

@Service
public class MangaService {

    private final MangaRepository repository;
    private final UserService userService;
    private final DtoMapperService dtoMapperService;
    private final MangaGetterFactoryService factoryService;

    public List<SimpleMangaDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(manga -> dtoMapperService.mangaTSimpleMangaDTO(manga, MangareaderApplication.API_MANGA_URL_PREFIX))
                .collect(Collectors.toList());
    }

    public MangaDTO createFromUrl(String url) {
        return factoryService
                .getInstance(url)
                .getFromUrl();
    }

    public void checkIfExistsAndCreateOrUpdate(MangaDTO dto, String url) {
        Manga manga = new Manga(null, dto.getName(), dto.getCover(), dto.getUrl(), new HashSet<User>());

        Manga persisted = repository.findByUrl(url).orElse(null);

        if (persisted != null) {
            manga.setId(persisted.getId());
            manga.setUsers(persisted.getUsers());
        }

        User logged = userService.getLogged();
        manga.getUsers().add(logged);
        repository.save(manga);
        
    }

}
