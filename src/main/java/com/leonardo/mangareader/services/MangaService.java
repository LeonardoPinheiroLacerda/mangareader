package com.leonardo.mangareader.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.leonardo.mangareader.MangareaderApplication;
import com.leonardo.mangareader.dtos.MangaDTO;
import com.leonardo.mangareader.dtos.MangaMetadataDTO;
import com.leonardo.mangareader.dtos.NewCoverDTO;
import com.leonardo.mangareader.dtos.SimpleMangaDTO;
import com.leonardo.mangareader.exceptions.ObjectNotFoundException;
import com.leonardo.mangareader.models.Chapter;
import com.leonardo.mangareader.models.Manga;
import com.leonardo.mangareader.repositories.MangaRepository;
import com.leonardo.mangareader.services.factories.MangaGetterFactoryService;
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

    public Optional<Manga> findByUrl(String url){
        return repository.findByUrl(url);
    }

    @Transactional
    public MangaDTO createFromUrl(String url) {

        //BUG UNION
        if(url.contains("pagina-manga")){
            url = url.replace("pagina-manga", "manga");
        }

        //BUG GOLDEN
        if(url.contains("/mangabr/")){
            url = url.replace("/mangabr/", "/mangas/");
        }


        MangaGetter getter = factoryService.getInstance(url);

        Manga manga = repository.findByUrl(url).orElse(null);

        Manga updated = getter.getFromUrl();

        if(manga != null){

            if(manga.getChapters().size() != updated.getChapters().size()){

                for(Chapter chapter : updated.getChapters()){
                    if(!manga.getChapters().contains(chapter)){

                        chapter.setManga(manga);
                        Chapter newChapter = new Chapter(null, 0L, chapter.getUrl(), chapter.getTitle(), chapter.getManga());

                        manga.getChapters().add(chapterService.create(newChapter));
                    }
                }

            }

            manga.setScore(updated.getScore());
            manga.setScoredBy(updated.getScoredBy());
            manga.setStatus(updated.getStatus());

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

            List<Chapter> persistedChapters = new ArrayList<>();

            manga.getChapters().forEach(chapter -> {
                Chapter persisted = chapterService.create(chapter);
                persistedChapters.add(persisted);
            });

            manga.setChapters(persistedChapters);

            repository.save(manga);
            return dtoMapperService.mangaToMangaDTO(updated);
        }

    }

    @Transactional
    public MangaMetadataDTO getMetadataFromUrl(String url){
        Manga manga = repository.findByUrl(url).orElseThrow(() -> new ObjectNotFoundException("O manga de URL " + url + " não pode ser encontrado."));
        return dtoMapperService.mangaToMangaMetadataDTO(manga);
    }

    @Transactional
    public void setCoverImage(NewCoverDTO dto){
        Manga manga = repository.findByUrl(dto.getUrl()).orElseThrow(() -> new ObjectNotFoundException("Um manga com a seguinte URL não foi localizado."));
        manga.setCover(dto.getCover());
        manga = repository.save(manga);
    }
    
}
