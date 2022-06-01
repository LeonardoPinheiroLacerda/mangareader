package com.leonardo.mangareader.webscraping.mangaGetter;

import java.io.IOException;

import com.leonardo.mangareader.MangareaderApplication;
import com.leonardo.mangareader.dtos.AuthorDTO;
import com.leonardo.mangareader.dtos.ChapterDTO;
import com.leonardo.mangareader.dtos.GenreDTO;
import com.leonardo.mangareader.dtos.MangaDTO;
import com.leonardo.mangareader.dtos.StatusDTO;
import com.leonardo.mangareader.dtos.enums.StatusEnumDTO;
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

            Elements statusEl = document.select("body > article > div.container.manga > div.row > div.col-sm-8 > div.row > div.col-sm-8 > h5:nth-child(6) > a");

            String status = statusEl.text();
            String statusHref = URL_PREFIX + statusEl.attr("href");

            StatusEnumDTO statusEnum = null;
            if(status.equals("Ativo")){
                statusEnum = StatusEnumDTO.ATIVO;
            }else if(status.equals("Completo")){
                statusEnum = StatusEnumDTO.COMPLETO;
            }

            String scoreText = document.select("body > article > div.container.manga > div.row > div.col-sm-8 > div.row > div.col-sm-8 > h2:nth-child(2)").text();

            String[] scoreSplited = scoreText.split(" ");

            String score = scoreSplited[0].replace("#", "");
            String scoredBy = scoreSplited[1];

            Elements authorEl = document.select("body > article > div.container.manga > div.row > div.col-sm-8 > div.row > div.col-sm-8 > h5:nth-child(4) > a");
            String authorName = authorEl.text();
            String authorHref = URL_PREFIX + authorEl.attr("href");        
            
            
            Elements artistEl = document.select("body > article > div.container.manga > div.row > div.col-sm-8 > div.row > div.col-sm-8 > h5:nth-child(5) > a");
            String artistName = artistEl.text();
            String artistHref = URL_PREFIX + artistEl.attr("href");        


            dto.setAuthor(new AuthorDTO(authorName, authorHref));
            dto.setArtist(new AuthorDTO(artistName, artistHref));
            dto.setStatus(new StatusDTO(statusHref, statusEnum));
            dto.setUrl(url);
            dto.setApiUrl(MangareaderApplication.API_MANGA_URL_PREFIX + url);
            dto.setTitle(title);
            dto.setCover(URL_PREFIX + cover);
            dto.setSynopsis(synopsis);
            dto.setScore(score);
            dto.setScoredBy(scoredBy);

            return dto;
        }catch(IOException | NullPointerException e){
            throw new SourceException("Erro ao tentar recuperar o manga de " + url);
        }
        
    }
    
}
