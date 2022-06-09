package com.leonardo.mangareader.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.leonardo.mangareader.MangareaderApplication;
import com.leonardo.mangareader.dtos.MangaDTO;
import com.leonardo.mangareader.dtos.SimpleMangaDTO;
import com.leonardo.mangareader.models.Chapter;
import com.leonardo.mangareader.models.Manga;
import com.leonardo.mangareader.models.enums.ReadStatus;
import com.leonardo.mangareader.repositories.MangaRepository;
import com.leonardo.mangareader.webscraping.mangaGetter.MangaGetter;

import lombok.AllArgsConstructor;

@AllArgsConstructor

@Service
public class MangaService {

    private final MangaRepository repository;
    private final ChapterService chapterService;
    private final GenreService genreService;
    private final AuthorService authorService;
    private final ArtistService artistService;
    private final DtoMapperService dtoMapperService;
    private final MangaGetterFactoryService factoryService;

    public List<SimpleMangaDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(manga -> dtoMapperService.mangaToSimpleMangaDTO(manga, MangareaderApplication.API_MANGA_URL_PREFIX))
                .collect(Collectors.toList());
    }

    @Transactional
    public MangaDTO createFromUrl(String url) {

        MangaGetter getter = factoryService.getInstance(url);

        Manga manga = repository.findByUrl(url).orElse(null);

        Manga updated = getter.getFromUrl();

        if(manga != null){

            if(manga.getChapters().size() != updated.getChapters().size()){

                for(Chapter chapter : updated.getChapters()){
                    if(!manga.getChapters().contains(chapter)){

                        chapter.setManga(manga);
                        Chapter newChapter = new Chapter(null, chapter.getUrl(), chapter.getTitle(), null, chapter.getManga(), null, null, ReadStatus.NONE);

                        manga.getChapters().add(chapterService.create(newChapter));
                    }
                }

            }

            manga.setCover(updated.getCover());

            repository.save(manga);
            return dtoMapperService.mangaToMangaDTO(manga);
        }else{

            manga = updated;
           
            manga.setGenres(
                genreService.createSet(manga.getGenres())
            );

            manga.setAuthor(
                authorService.create(manga.getAuthor())
            );
            manga.setArtist(
                artistService.create(manga.getArtist())
            );

            repository.save(manga);
            return dtoMapperService.mangaToMangaDTO(updated);
        }

    }
    
}
