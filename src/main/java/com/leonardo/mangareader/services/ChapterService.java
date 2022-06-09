package com.leonardo.mangareader.services;

import org.springframework.stereotype.Service;

import com.leonardo.mangareader.dtos.DetailedChapterDTO;
import com.leonardo.mangareader.models.Chapter;
import com.leonardo.mangareader.models.enums.ReadStatus;
import com.leonardo.mangareader.repositories.ChapterRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor

@Service
public class ChapterService {

    private final ChapterGetterFactoryService factoryService;
    private final ChapterRepository repository;

    public DetailedChapterDTO getFromUrl(String url) {
        return factoryService
            .getInstance(url)
            .getFromUrl();
    }

    public Chapter create(Chapter chapter){
        return repository.save(new Chapter(null, chapter.getUrl(), chapter.getTitle(), null, chapter.getManga(), null, null, ReadStatus.NONE));
    }
}
