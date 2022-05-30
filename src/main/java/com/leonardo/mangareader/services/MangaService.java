package com.leonardo.mangareader.services;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import com.leonardo.mangareader.dtos.ChapterDTO;
import com.leonardo.mangareader.dtos.GenreDTO;
import com.leonardo.mangareader.dtos.MangaDTO;
import com.leonardo.mangareader.dtos.SimpleMangaDTO;
import com.leonardo.mangareader.models.Manga;
import com.leonardo.mangareader.models.User;
import com.leonardo.mangareader.repositories.MangaRepository;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor

@Service
public class MangaService {
    
    private final MangaRepository repository;
    private final UserService userService;
    private final DtoMapperService dtoMapperService;

    private static final String URL_PREFIX = "https://goldenmangas.top";

    private static final String API_MANGA_URL_PREFIX = "/api/mangas?url=";
    private static final String API_CHAPTER_URL_PREFIX = "/api/chapters?url=";

    public List<SimpleMangaDTO> findAll(){
        return repository.findAll()
            .stream()
            .map(manga -> dtoMapperService.mangaTSimpleMangaDTO(manga, API_MANGA_URL_PREFIX))
            .collect(Collectors.toList());
    }

    public MangaDTO createFromUrl(String url){
        
        try{
            Document document = Jsoup.connect(url).get();
           
            MangaDTO dto = new MangaDTO();

            String name = document.select("body > article > div.container.manga > div.row > div.col-sm-8 > div.row > div.col-sm-8 > h2:nth-child(1)").text();
            String cover = document.select("body > article > div.container.manga > div.row > div.col-sm-8 > div.row > div.col-sm-4.text-right > img").attr("src");

            Integer chaptersCount = document.select("#capitulos").first().children().size();

            List<ChapterDTO> chapters = new ArrayList<>();

            for(int i = 1; i <= chaptersCount; i ++){
                
                String chapterUrl = URL_PREFIX + document.select("#capitulos > li:nth-child(" + i + ") > a").attr("href");                
                String chapterNumber = document.select("#capitulos > li:nth-child(" + i + ") > a > div.col-sm-5").text();
                String chapterApiUrl = API_CHAPTER_URL_PREFIX + chapterUrl;

                chapters.add(new ChapterDTO(chapterUrl, chapterNumber, chapterApiUrl));
            }

            String synopsis = document.select("#manga_capitulo_descricao").text();

            List<GenreDTO> genres = new ArrayList<>();

            Elements genresEls = document.select("body > article > div.container.manga > div.row > div.col-sm-8 > div.row > div.col-sm-8 > h5:nth-child(3)").get(0).children();

            for(int i = 3; i <= genresEls.size(); i ++){

                Elements genre = document.select("body > article > div.container.manga > div.row > div.col-sm-8 > div.row > div.col-sm-8 > h5:nth-child(3) > a:nth-child(" + i + ")");
                
                String genreUrl = genre.text();
                String genreLink = genre.attr("href");

                genreLink = genreLink.replace("..", URL_PREFIX);

                genres.add(new GenreDTO(genreUrl, genreLink));
            }

            dto.setUrl(url);
            dto.setApiUrl(API_MANGA_URL_PREFIX + url);
            dto.setName(name);
            dto.setCover(URL_PREFIX + cover);
            dto.setChapters(chapters);
            dto.setSynopsis(synopsis);
            dto.setGenres(genres);

            Manga manga = new Manga(null, dto.getName(), dto.getCover(), dto.getUrl(), new HashSet<User>());
            
            Manga persisted = repository.findByUrl(url).orElse(null);

            if(persisted != null){
                manga.setId(persisted.getId());
                manga.setUsers(persisted.getUsers());
            }

            User logged = userService.getLogged();

            manga.getUsers().add(logged);

            repository.save(manga);

            return dto;
        }catch(IOException e){
            e.printStackTrace();
        }

        return new MangaDTO();
    }

}
