package com.leonardo.mangareader.services;

import com.leonardo.mangareader.exceptions.NotSuportedSourceException;
import com.leonardo.mangareader.webscraping.chapterGetter.ChapterGetter;
import com.leonardo.mangareader.webscraping.chapterGetter.GoldenMangasChapterGetter;
import com.leonardo.mangareader.webscraping.chapterGetter.MangaHostedChapterGetter;

import org.springframework.stereotype.Service;

@Service
public class ChapterGetterFactoryService {
    
    public ChapterGetter getInstance(String url){

        final String GOLDEN_MANGAS = "https://goldenmangas.top/";
        final String MANGA_HOSTED = "https://mangahosted.com/";

        if(url.startsWith(GOLDEN_MANGAS)){
            return new GoldenMangasChapterGetter(url);
        } else if(url.startsWith(MANGA_HOSTED)){
            return new MangaHostedChapterGetter(url);
        }

        throw new NotSuportedSourceException("Source não suportada, acesse a página /help para consultar os sources válidos.");

    }

}
