package com.leonardo.mangareader.webscraping.chapterGetter;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.leonardo.mangareader.MangareaderApplication;
import com.leonardo.mangareader.dtos.DetailedChapterDTO;
import com.leonardo.mangareader.exceptions.SourceException;

import lombok.AllArgsConstructor;

@AllArgsConstructor

public class GoldenMangasChapterGetter implements ChapterGetter{

    private String url;

    private static final String URL_PREFIX = "https://goldenmangas.top";

    @Override
    public DetailedChapterDTO getFromUrl() {
        try{

            Document document = Jsoup.connect(url).get();

            DetailedChapterDTO dto = new DetailedChapterDTO();

            Elements pages = document.select("#capitulos_images > center").get(0).children();

            for(int i = 0; i < pages.size(); i ++){
                dto.getPages().add(URL_PREFIX + pages.get(i).attr("src"));
            }
 
            String title = document.select("body > header.breadcrumbs > div > h1").text();

            String mangaUrl = document.select("body > header.breadcrumbs > div > ul > li:nth-child(5) > a").attr("href");

            Integer count = 1;
            
            while(!document.select("#capitulo_trocar > option:nth-child(" + count +")").hasAttr("selected")){
                count += 1;
            }

            String currentChapter = document.select("#capitulo_trocar > option:nth-child(" + count +")").text();

            String urlPrefix = url.substring(0, url.length() - currentChapter.length());

            String previous = document.select("#capitulo_trocar > option:nth-child(" + (count - 1) +")").text();
            String next = document.select("#capitulo_trocar > option:nth-child(" + (count + 1) +")").text();

            if(!previous.equals("")){
                dto.setApiPreviousUrl(MangareaderApplication.API_CHAPTER_DOWNLOAD_URL_PREFIX + urlPrefix + previous);
            }

            if(!next.equals("")){
                dto.setApiNextUrl(MangareaderApplication.API_CHAPTER_URL_PREFIX + urlPrefix + next);
            }

            dto.setCurrentChapter(currentChapter);

            dto.setTitle(title);
            dto.setMangaUrl(URL_PREFIX + mangaUrl);
            dto.setApiDownloadUrl(MangareaderApplication.API_CHAPTER_URL_PREFIX + mangaUrl);
            dto.setChapterUrl(url);
            return dto;

        }catch(IOException | NullPointerException e){
            throw new SourceException("Erro ao tentar recuperar o capítulo de " + url);
        }
    }
    
}
