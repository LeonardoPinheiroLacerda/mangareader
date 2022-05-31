package com.leonardo.mangareader.services;

import com.leonardo.mangareader.exceptions.NotSuportedSourceException;
import com.leonardo.mangareader.webscraping.mangaGetter.GoldenMangasGetter;
import com.leonardo.mangareader.webscraping.mangaGetter.MangaGetter;

import org.springframework.stereotype.Service;

@Service
public class MangaGetterFactoryService {
    
    public MangaGetter getInstance(String url){

        final String GOLDEN_MANGAS = "https://goldenmangas.top/";
        
        if(url.startsWith(GOLDEN_MANGAS)){
            return new GoldenMangasGetter(url);
        }

        throw new NotSuportedSourceException("Source não suportada, acesse a página /help para consultar os sources válidos.");

    }

}
