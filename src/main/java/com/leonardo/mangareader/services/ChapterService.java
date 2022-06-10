package com.leonardo.mangareader.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.leonardo.mangareader.dtos.DetailedChapterDTO;
import com.leonardo.mangareader.exceptions.ObjectNotFoundException;
import com.leonardo.mangareader.models.Chapter;
import com.leonardo.mangareader.models.enums.ReadStatus;
import com.leonardo.mangareader.repositories.ChapterRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor

@Service
public class ChapterService {

    private final ChapterGetterFactoryService factoryService;
    private final ChapterRepository repository;

    @Transactional
    public DetailedChapterDTO getFromUrl(String url) {

        Chapter chapter = repository.findByUrl(url).orElseThrow(() -> new ObjectNotFoundException("O Capítulo não pode ser encontrado, provavelmente o manga ainda não foi importado."));

        if(chapter.getReadStatus().equals(ReadStatus.NONE)){
            chapter.setReadStatus(ReadStatus.VIEWED);
        }

        repository.save(chapter);

        return factoryService
            .getInstance(url)
            .getFromUrl();
    }

    public Optional<Chapter> findByUrl(String url){
        return repository.findByUrl(url);
    }

    public Chapter create(Chapter chapter){
        return repository.save(new Chapter(null, chapter.getUrl(), chapter.getTitle(), chapter.getManga(), ReadStatus.NONE));
    }
}
