package com.leonardo.mangareader.services;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.leonardo.mangareader.MangareaderApplication;
import com.leonardo.mangareader.dtos.ArtistDTO;
import com.leonardo.mangareader.dtos.AuthorDTO;
import com.leonardo.mangareader.dtos.ChapterDTO;
import com.leonardo.mangareader.dtos.ChapterHistoryDTO;
import com.leonardo.mangareader.dtos.GenreDTO;
import com.leonardo.mangareader.dtos.MangaDTO;
import com.leonardo.mangareader.dtos.MangaHistoryDTO;
import com.leonardo.mangareader.dtos.MangaMetadataDTO;
import com.leonardo.mangareader.dtos.SimpleMangaDTO;
import com.leonardo.mangareader.dtos.UserDTO;
import com.leonardo.mangareader.models.Artist;
import com.leonardo.mangareader.models.Author;
import com.leonardo.mangareader.models.Chapter;
import com.leonardo.mangareader.models.ChapterHistory;
import com.leonardo.mangareader.models.Genre;
import com.leonardo.mangareader.models.Manga;
import com.leonardo.mangareader.models.MangaHistory;
import com.leonardo.mangareader.models.User;
import com.leonardo.mangareader.models.pks.ChapterHistoryPK;
import com.leonardo.mangareader.repositories.ChapterHistoryRepository;
import com.leonardo.mangareader.security.AppUserDetails;

import lombok.AllArgsConstructor;

@AllArgsConstructor

@Service
public class DtoMapperService {
    
    private final ChapterHistoryRepository chapterHistoryRepository;

    private User getLogged(){
        return ((AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
    }

    public UserDTO userToUserDTO(User user){
        return new UserDTO(
            user.getId(), 
            user.getUsername(), 
            user.getEmail()
        );
    }

    public SimpleMangaDTO mangaToSimpleMangaDTO(Manga manga, String apiUrlPrefix){
        return new SimpleMangaDTO(
            manga.getTitle(), 
            manga.getCover(), 
            manga.getUrl(), 
            apiUrlPrefix + manga.getUrl()
        );
    }


    public MangaDTO mangaToMangaDTO(Manga manga){

        manga.setChapters(
            manga.getChapters()
                .stream()
                .sorted((ch1, ch2) -> ch2.getId().compareTo(ch1.getId()))
                .collect(Collectors.toList())
        );
        
        MangaDTO dto = new MangaDTO();

        dto.setTitle(manga.getTitle());
        dto.setCover(manga.getCover());
        dto.setUrl(manga.getUrl());
        dto.setSynopsis(manga.getSynopsis());

        dto.setStatus(manga.getStatus());
        dto.setScore(manga.getScore());
        dto.setScoredBy(manga.getScoredBy());

        dto.setArtist(artistToArtistDTO(manga.getArtist()));
        dto.setAuthor(authorToAuthorDTO(manga.getAuthor()));

        dto.setGenres(
            manga.getGenres()
                .stream()
                .map(genre -> genreToGenreDTO(genre))
                .collect(Collectors.toList())
        );

        dto.setChapters(
            manga.getChapters()
            .stream()
            .map(chapter -> chapterToChapterDTO(chapter))
            .collect(Collectors.toList())
        );


        return dto;
    }

    public MangaMetadataDTO mangaToMangaMetadataDTO(Manga manga){

        manga.setChapters(
            manga.getChapters()
                .stream()
                .sorted((ch1, ch2) -> ch2.getId().compareTo(ch1.getId()))
                .collect(Collectors.toList())
        );
        
        MangaMetadataDTO dto = new MangaMetadataDTO();

        dto.setTitle(manga.getTitle());
        dto.setCover(manga.getCover());
        dto.setUrl(manga.getUrl());
        dto.setSynopsis(manga.getSynopsis());

        dto.setStatus(manga.getStatus());
        dto.setScore(manga.getScore());
        dto.setScoredBy(manga.getScoredBy());

        dto.setArtist(artistToArtistDTO(manga.getArtist()));
        dto.setAuthor(authorToAuthorDTO(manga.getAuthor()));

        dto.setGenres(
            manga.getGenres()
                .stream()
                .map(genre -> genreToGenreDTO(genre))
                .collect(Collectors.toList())
        );

        return dto;
    }


    public AuthorDTO authorToAuthorDTO(Author author){
        return new AuthorDTO(author.getName(), author.getUrl());
    }

    public ArtistDTO artistToArtistDTO(Artist artist){
        return new ArtistDTO(artist.getName(), artist.getUrl());
    }

    public GenreDTO genreToGenreDTO(Genre genre){
        return new GenreDTO(genre.getName(), genre.getUrl());
    }

    public ChapterDTO chapterToChapterDTO(Chapter chapter){
        User user = getLogged();
        ChapterHistory history = chapterHistoryRepository.findById(new ChapterHistoryPK(user, chapter)).orElse(new ChapterHistory());

        return new ChapterDTO(chapter.getUrl(), chapter.getTitle(), history.getReadStatus());
    }

    public MangaHistoryDTO mangaHistoryToMangaHistoryDTO(MangaHistory history){
        return new MangaHistoryDTO(
            mangaToMangaMetadataDTO(history.getId().getManga()), 
            history.getLastReadAt(),
            chapterToChapterDTO(history.getLastChapterRead())
        );
    }

    public ChapterHistoryDTO chapterHistoryTochapterHistoryDTO(ChapterHistory history){
        return new ChapterHistoryDTO(
            mangaToSimpleMangaDTO(history.getId().getChapter().getManga(), MangareaderApplication.API_MANGA_URL_PREFIX),
            chapterToChapterDTO(history.getId().getChapter()), 
            history.getLastReadAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), 
            history.getReadStatus()
        );
    }

}
