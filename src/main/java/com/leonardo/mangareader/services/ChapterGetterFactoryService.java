package com.leonardo.mangareader.services;

import com.leonardo.mangareader.exceptions.NotSuportedSourceException;
import com.leonardo.mangareader.webscraping.chapterGetter.ChapterGetter;
import com.leonardo.mangareader.webscraping.chapterGetter.GoldenMangasChapterGetter;
import com.leonardo.mangareader.webscraping.chapterGetter.UnionLeitorChapterGetter;

import org.springframework.stereotype.Service;

@Service
public class ChapterGetterFactoryService {
    
    public ChapterGetter getInstance(String url){

        final String GOLDEN_MANGAS = "https://goldenmangas.top/";
        final String UNION_LEITOR = "https://unionleitor.top/";

        final String HTTP_UNION_MANGAS = "http://unionmangas.top/";

        if(url.startsWith(GOLDEN_MANGAS)){
            return new GoldenMangasChapterGetter(url);
        } else if(url.startsWith(UNION_LEITOR) || url.startsWith(HTTP_UNION_MANGAS)){
            return new UnionLeitorChapterGetter(url);
        } 

        throw new NotSuportedSourceException("Source não suportada. 2");

    }

}
