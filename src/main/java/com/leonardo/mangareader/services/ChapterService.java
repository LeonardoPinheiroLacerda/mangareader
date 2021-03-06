package com.leonardo.mangareader.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.leonardo.mangareader.dtos.DetailedChapterDTO;
import com.leonardo.mangareader.exceptions.ObjectNotFoundException;
import com.leonardo.mangareader.models.Chapter;
import com.leonardo.mangareader.models.User;
import com.leonardo.mangareader.models.pks.ChapterHistoryPK;
import com.leonardo.mangareader.repositories.ChapterRepository;
import com.leonardo.mangareader.services.factories.ChapterGetterFactoryService;

import lombok.AllArgsConstructor;

@AllArgsConstructor

@Service
public class ChapterService {

    private final ChapterGetterFactoryService factoryService;
    private final ChapterRepository repository;
    private final ChapterHistoryService chapterHistoryService;
    private final UserService userService;

    @Transactional
    public DetailedChapterDTO getFromUrl(String url) {

        Chapter chapter = repository.findByUrl(url).orElse(null);

        if(chapter == null){

            //Tratando variações de url da UNION MANGAS / UNION LEITOR
            if(url.contains("https://unionleitor.top/leitor/")){
                url = url.replace("https://unionleitor.top/leitor/", "http://unionmangas.top/leitor/");
            }else if(url.contains("http://unionmangas.top/leitor/")){
                url = url.replace("http://unionmangas.top/leitor/", "https://unionleitor.top/leitor/");
            }

            chapter = repository.findByUrl(url).orElseThrow(() -> new ObjectNotFoundException("O Capítulo não pode ser encontrado, provavelmente o manga ainda não foi importado."));
        }

        repository.save(chapter);

        return factoryService
            .getInstance(url)
            .getFromUrl();
    }

    @Transactional
    public Optional<Chapter> findByUrl(String url){
        return repository.findByUrl(url);
    }

    @Transactional
    public Optional<Chapter> findById(Long id){
        return repository.findById(id);
    }

    public Chapter create(Chapter chapter){
        User logged = userService.getLogged();
        chapter = repository.save(new Chapter(null, 0L, chapter.getUrl(), chapter.getTitle(), chapter.getManga()));

        ChapterHistoryPK pk = new ChapterHistoryPK(logged, chapter);
        chapterHistoryService.doHistory(pk);

        return chapter;
    }
}
