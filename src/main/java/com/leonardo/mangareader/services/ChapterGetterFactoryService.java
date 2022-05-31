package com.leonardo.mangareader.services;

import com.leonardo.mangareader.exceptions.NotSuportedSourceException;
import com.leonardo.mangareader.webscraping.chapterGetter.ChapterGetter;
import com.leonardo.mangareader.webscraping.chapterGetter.GoldenMangasChapterGetter;

import org.springframework.stereotype.Service;

@Service
public class ChapterGetterFactoryService {
    
    public ChapterGetter getInstance(String url){

        final String GOLDEN_MANGAS = "https://goldenmangas.top/";

        if(url.startsWith(GOLDEN_MANGAS)){
            return new GoldenMangasChapterGetter(url);
        } 

        throw new NotSuportedSourceException("Source não suportada, acesse a página /help para consultar os sources válidos.");

    }

}
