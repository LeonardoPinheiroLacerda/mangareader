package com.leonardo.mangareader.services;

import com.leonardo.mangareader.exceptions.NotSuportedSourceException;
import com.leonardo.mangareader.webscraping.mangaGetter.GoldenMangasGetter;
import com.leonardo.mangareader.webscraping.mangaGetter.MangaGetter;
import com.leonardo.mangareader.webscraping.mangaGetter.UnionLeitorGetter;

import org.springframework.stereotype.Service;

@Service
public class MangaGetterFactoryService {
    
    public MangaGetter getInstance(String url){

        final String GOLDEN_MANGAS = "https://goldenmangas.top/";
        final String UNION_LEITOR_MANGAS = "https://unionleitor.top/";
        
        if(url.startsWith(GOLDEN_MANGAS)){
            return new GoldenMangasGetter(url);
        }else if(url.startsWith(UNION_LEITOR_MANGAS)){
            return new UnionLeitorGetter(url);
        }

        throw new NotSuportedSourceException("Source não suportada, acesse a página /help para consultar os sources válidos.");

    }

}
