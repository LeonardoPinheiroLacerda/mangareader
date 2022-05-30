package com.leonardo.mangareader.webscraping.chapterGetter;

import java.io.IOException;

import com.leonardo.mangareader.dtos.ChapterPagesDTO;
import com.leonardo.mangareader.exceptions.SourceException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import lombok.AllArgsConstructor;

@AllArgsConstructor

public class GoldenMangasChapterGetter implements ChapterGetter{

    private String url;

    private static final String URL_PREFIX = "https://goldenmangas.top";

    @Override
    public ChapterPagesDTO getFromUrl() {
        try{

            Document document = Jsoup.connect(url).get();

            ChapterPagesDTO dto = new ChapterPagesDTO();

            Elements pages = document.select("#capitulos_images > center").get(0).children();

            for(int i = 0; i < pages.size(); i ++){
                dto.getPages().add(URL_PREFIX + pages.get(i).attr("src"));
            }
 
            String title = document.select("body > header.breadcrumbs > div > h1").text();

            String mangaUrl = document.select("body > header.breadcrumbs > div > ul > li:nth-child(5) > a").attr("href");

            dto.setTitle(title);
            dto.setMangaUrl(URL_PREFIX + mangaUrl);
            dto.setUrl(url);
            return dto;

        }catch(IOException e){
            throw new SourceException("Erro ao tentar recuperar o capítulo de " + url);
        }
    }
    
}
