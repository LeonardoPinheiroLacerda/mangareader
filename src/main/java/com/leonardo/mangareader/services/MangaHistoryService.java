package com.leonardo.mangareader.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.leonardo.mangareader.dtos.DetailedChapterDTO;
import com.leonardo.mangareader.dtos.MangaHistoryDTO;
import com.leonardo.mangareader.exceptions.ObjectNotFoundException;
import com.leonardo.mangareader.models.Chapter;
import com.leonardo.mangareader.models.Manga;
import com.leonardo.mangareader.models.MangaHistory;
import com.leonardo.mangareader.models.User;
import com.leonardo.mangareader.models.pks.ChapterHistoryPK;
import com.leonardo.mangareader.models.pks.MangaHistoryPK;
import com.leonardo.mangareader.repositories.MangaHistoryRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor

@Service 
public class MangaHistoryService {
    
    private final MangaHistoryRepository repository;
    private final MangaService mangaService;
    private final ChapterService chapterService;
    private final ChapterHistoryService chapterHistoryService;
    private final UserService userService;
    private final DtoMapperService dtoMapperService;
    
    @Transactional
    public MangaHistoryDTO findById(MangaHistoryPK id){
        MangaHistory obj = repository.findById(id).orElseGet(null);
        
        if(obj == null){
            return null;
        }

        return dtoMapperService.mangaHistoryToMangaHistoryDTO(obj);
    }
    @Transactional
    public List<MangaHistoryDTO> getUserHistory(){
        List<MangaHistory> history =  repository
            .findUserHistory(userService.getLogged())
            .get();
        
        return history
            .stream()
            .map(h -> dtoMapperService.mangaHistoryToMangaHistoryDTO(h))
            .sorted((h1, h2) -> h2.getLastRead().compareTo(h1.getLastRead()))
            .collect(Collectors.toList());
    }

    @Transactional
    public MangaHistoryDTO getMangaHistory(String url){
        Manga manga = mangaService.findByUrl(url).orElse(null);

        if(manga == null){
            //Salva no banco e recupera a URL atualizada
            url = mangaService.createFromUrl(url).getUrl();
            manga = mangaService.findByUrl(url).get();
        }

        User user = userService.getLogged();

        MangaHistoryPK pk = new MangaHistoryPK(user, manga);

        MangaHistory history = repository.findById(pk).orElse(null);

        if(history == null)
            history = doHistory(pk);

        return dtoMapperService.mangaHistoryToMangaHistoryDTO(history);
    }

    @Transactional
    public MangaHistory doHistory(DetailedChapterDTO dto) {

        String chapterUrl = dto.getChapterUrl();
        String mangaUrl = dto.getMangaUrl();

        Manga manga = mangaService.findByUrl(mangaUrl).orElseThrow(() -> new ObjectNotFoundException("Manga de link: " + mangaUrl + " não pode ser encontrado para registrar um histórico de leitura."));
        User logged = userService.getLogged();
        LocalDateTime timesptamp = LocalDateTime.now();
        Chapter chapter = chapterService.findByUrl(chapterUrl).orElseThrow(() -> new ObjectNotFoundException("Capítulo de link: " + chapterUrl + " não pode ser encontrado para registrar um histórico de leitura."));

        MangaHistoryPK pk = new MangaHistoryPK(logged, manga);
        MangaHistory history = new MangaHistory(pk, timesptamp, chapter);

        return repository.save(history);
    }

    @Transactional
    public MangaHistory doHistory(MangaHistoryPK pk) {
        MangaHistory history = new MangaHistory(pk, LocalDateTime.now(), pk.getManga().getChapters().get(pk.getManga().getChapters().size() - 1));
        return repository.save(history);
    }

    @Transactional
    public void delete(String url) {

        Manga manga = mangaService.findByUrl(url).orElseThrow(() -> new ObjectNotFoundException("Não foi possível localizar o manga de URL " + url + " para remove-lo do seu histórico"));
        User user = userService.getLogged();

        MangaHistoryPK pk = new MangaHistoryPK(user, manga);

        MangaHistory history = repository.findById(pk).orElseThrow(() -> new ObjectNotFoundException("Não foi possível localizar o manga de URL " + url + " no seu histórico"));

        manga.getChapters().forEach(chapter -> {
            ChapterHistoryPK chapterPk = new ChapterHistoryPK(user, chapter);
            chapterHistoryService.delete(chapterPk);
        });

        repository.delete(history);
    }

}
