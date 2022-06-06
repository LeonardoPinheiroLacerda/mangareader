package com.leonardo.mangareader.webscraping.chapterGetter;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.leonardo.mangareader.MangareaderApplication;
import com.leonardo.mangareader.dtos.DetailedChapterDTO;
import com.leonardo.mangareader.exceptions.SourceException;

import lombok.AllArgsConstructor;

@AllArgsConstructor

public class UnionLeitorChapterGetter implements ChapterGetter{

    private String url;

    @Override
    public DetailedChapterDTO getFromUrl() {
        

        try{

            Document document = Jsoup.connect(url).get();

            DetailedChapterDTO dto = new DetailedChapterDTO();

            Integer count = 1;
        
            while(!document.select("#capitulo_trocar > option:nth-child(" + count +")").hasAttr("selected")){
                count += 1;
            }

            String current = document.select("#capitulo_trocar > option:nth-child(" + count +")").text();

            String urlPrefix = url.substring(0, url.length() - current.length());

            String previous = document.select("#capitulo_trocar > option:nth-child(" + (count - 1) +")").text();
            String next = document.select("#capitulo_trocar > option:nth-child(" + (count + 1) +")").text();

            if(!previous.equals("")){
                dto.setApiPreviousUrl(MangareaderApplication.API_CHAPTER_URL_PREFIX + urlPrefix + previous);
            }

            if(!next.equals("")){
                dto.setApiNextUrl(MangareaderApplication.API_CHAPTER_URL_PREFIX + urlPrefix + next);
            }

            String mangaUrl = document.select("body > div.breadcrumbs > div > div > a:nth-child(3)").attr("href");
            String title = document.select("body > div.breadcrumbs > div > div > a:nth-child(3)").text();

            Elements pages = document.select(".img-manga");

            for(Element page : pages){
                String url = page.attr("src");
                dto.getPages().add(url);    
            }
            
            dto.setTitle(title);
            dto.setMangaUrl(mangaUrl);
            dto.setApiDownloadUrl(MangareaderApplication.API_CHAPTER_DOWNLOAD_URL_PREFIX + url);
            dto.setCurrentChapter(current);
            dto.setChapterUrl(url);
            return dto;

        }catch(IOException | NullPointerException e){
            throw new SourceException("Erro ao tentar recuperar o capítulo de " + url);
        }

    }
    
}
