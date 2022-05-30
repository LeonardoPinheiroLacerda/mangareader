package com.leonardo.mangareader.services;

import com.leonardo.mangareader.exceptions.NotSuportedSourceException;
import com.leonardo.mangareader.webscraping.mangaGetter.GoldenMangasGetter;
import com.leonardo.mangareader.webscraping.mangaGetter.MangaGetter;
import com.leonardo.mangareader.webscraping.mangaGetter.MangaHostedGetter;

import org.springframework.stereotype.Service;

@Service
public class MangaGetterFactoryService {
    
    public MangaGetter getInstance(String url){

        final String GOLDEN_MANGAS = "https://goldenmangas.top/";
        final String MANGA_HOSTED = "https://mangahosted.com/";

        if(url.startsWith(GOLDEN_MANGAS)){
            return new GoldenMangasGetter(url);
        } else if(url.startsWith(MANGA_HOSTED)){
            return new MangaHostedGetter(url);
        }

        throw new NotSuportedSourceException("Source não suportada, acesse a página /help para consultar os sources válidos.");

    }

}
