package com.leonardo.mangareader.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.leonardo.mangareader.dtos.DetailedChapterDTO;
import com.leonardo.mangareader.dtos.HistoryDTO;
import com.leonardo.mangareader.exceptions.ObjectNotFoundException;
import com.leonardo.mangareader.models.Chapter;
import com.leonardo.mangareader.models.History;
import com.leonardo.mangareader.models.Manga;
import com.leonardo.mangareader.models.User;
import com.leonardo.mangareader.models.pks.HistoryPK;
import com.leonardo.mangareader.repositories.HistoryRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor

@Service 
public class HistoryService {
    
    private final HistoryRepository repository;
    private final MangaService mangaService;
    private final ChapterService chapterService;
    private final UserService userService;
    private final DtoMapperService dtoMapperService;
    
    @Transactional
    public HistoryDTO findById(HistoryPK id){
        History obj = repository.findById(id).orElseGet(null);
        
        if(obj == null){
            return null;
        }

        return dtoMapperService.historyToHistoryDTO(obj);
    }
    @Transactional
    public List<HistoryDTO> getUserHistory(){
        List<History> history =  repository
            .findUserHistory(userService.getLogged())
            .get();
        
        return history
            .stream()
            .map(h -> dtoMapperService.historyToHistoryDTO(h))
            .sorted((h1, h2) -> h2.getLastRead().compareTo(h1.getLastRead()))
            .collect(Collectors.toList());
    }

    @Transactional
    public HistoryDTO getMangaHistory(String url){
        Manga manga = mangaService.findByUrl(url).orElse(null);

        if(manga == null){
            //Salva no banco e recupera a URL atualizada
            url = mangaService.createFromUrl(url).getUrl();
            manga = mangaService.findByUrl(url).get();
        }

        User user = userService.getLogged();

        HistoryPK pk = new HistoryPK(user, manga);

        History history = repository.findById(pk).orElse(null);

        if(history == null)
            history = doHistory(pk);

        return dtoMapperService.historyToHistoryDTO(history);
    }

    @Transactional
    public History doHistory(DetailedChapterDTO dto) {

        String chapterUrl = dto.getChapterUrl();
        String mangaUrl = dto.getMangaUrl();

        Manga manga = mangaService.findByUrl(mangaUrl).orElseThrow(() -> new ObjectNotFoundException("Manga de link: " + mangaUrl + " não pode ser encontrado para registrar um histórico de leitura."));
        User logged = userService.getLogged();
        LocalDateTime timesptamp = LocalDateTime.now();
        Chapter chapter = chapterService.findByUrl(chapterUrl).orElseThrow(() -> new ObjectNotFoundException("Capítulo de link: " + chapterUrl + " não pode ser encontrado para registrar um histórico de leitura."));

        HistoryPK pk = new HistoryPK(logged, manga);
        History history = new History(pk, timesptamp, chapter);

        return repository.save(history);
    }

    @Transactional
    public History doHistory(HistoryPK pk) {
        History history = new History(pk, LocalDateTime.now(), pk.getManga().getChapters().get(pk.getManga().getChapters().size() - 1));
        return repository.save(history);
    }

    @Transactional
    public void delete(String url) {

        Manga manga = mangaService.findByUrl(url).orElseThrow(() -> new ObjectNotFoundException("Não foi possível localizar o manga de URL " + url + " para remove-lo do seu histórico"));
        User user = userService.getLogged();

        HistoryPK pk = new HistoryPK(user, manga);

        History history = repository.findById(pk).orElseThrow(() -> new ObjectNotFoundException("Não foi possível localizar o manga de URL " + url + " no seu histórico"));

        repository.delete(history);
    }

}
