package com.leonardo.mangareader.services;

import java.io.IOException;

import com.leonardo.mangareader.dtos.ChapterPagesDTO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public class ChapterService {

    private static final String URL_PREFIX = "https://goldenmangas.top";

    public ChapterPagesDTO getFromUrl(String url) {
        
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
            e.printStackTrace();
        }

        return new ChapterPagesDTO();
    }
    
}
