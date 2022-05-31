package com.leonardo.mangareader.webscraping.mangaGetter;

import java.io.IOException;

import com.leonardo.mangareader.MangareaderApplication;
import com.leonardo.mangareader.dtos.ChapterDTO;
import com.leonardo.mangareader.dtos.GenreDTO;
import com.leonardo.mangareader.dtos.MangaDTO;
import com.leonardo.mangareader.exceptions.SourceException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import lombok.AllArgsConstructor;

@AllArgsConstructor

public class GoldenMangasGetter implements MangaGetter{

    private static final String URL_PREFIX = "https://goldenmangas.top";

    private String url;

    @Override
    public MangaDTO getFromUrl() {
        try{
            Document document = Jsoup.connect(url).get();
           
            MangaDTO dto = new MangaDTO();

            String title = document.select("body > article > div.container.manga > div.row > div.col-sm-8 > div.row > div.col-sm-8 > h2:nth-child(1)").text();
            String cover = document.select("body > article > div.container.manga > div.row > div.col-sm-8 > div.row > div.col-sm-4.text-right > img").attr("src");
            String synopsis = document.select("#manga_capitulo_descricao").text();

            Elements chaptersEls = document.select("#capitulos").first().children();

            for(int i = 1; i <= chaptersEls.size(); i ++){
                
                String chapterUrl = URL_PREFIX + document.select("#capitulos > li:nth-child(" + i + ") > a").attr("href");                
                String chapterNumber = document.select("#capitulos > li:nth-child(" + i + ") > a > div.col-sm-5").text();
                String chapterApiUrl = MangareaderApplication.API_CHAPTER_URL_PREFIX + chapterUrl;

                dto.getChapters().add(new ChapterDTO(chapterUrl, chapterNumber, chapterApiUrl));
            }

            Elements genresEls = document.select("body > article > div.container.manga > div.row > div.col-sm-8 > div.row > div.col-sm-8 > h5:nth-child(3)").get(0).children();

            for(int i = 3; i <= genresEls.size(); i ++){

                Elements genre = document.select("body > article > div.container.manga > div.row > div.col-sm-8 > div.row > div.col-sm-8 > h5:nth-child(3) > a:nth-child(" + i + ")");
                
                String genreUrl = genre.text();
                String genreLink = genre.attr("href");

                genreLink = genreLink.replace("..", URL_PREFIX);

                dto.getGenres().add(new GenreDTO(genreUrl, genreLink));
            }

            dto.setUrl(url);
            dto.setApiUrl(MangareaderApplication.API_MANGA_URL_PREFIX + url);
            dto.setTitle(title);
            dto.setCover(URL_PREFIX + cover);
            dto.setSynopsis(synopsis);

            return dto;
        }catch(IOException | NullPointerException e){
            throw new SourceException("Erro ao tentar recuperar o manga de " + url);
        }
        
    }
    
}
