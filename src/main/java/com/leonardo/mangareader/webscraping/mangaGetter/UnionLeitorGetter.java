package com.leonardo.mangareader.webscraping.mangaGetter;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.leonardo.mangareader.MangareaderApplication;
import com.leonardo.mangareader.dtos.AuthorDTO;
import com.leonardo.mangareader.dtos.ChapterDTO;
import com.leonardo.mangareader.dtos.GenreDTO;
import com.leonardo.mangareader.dtos.MangaDTO;
import com.leonardo.mangareader.dtos.StatusDTO;
import com.leonardo.mangareader.exceptions.SourceException;
import com.leonardo.mangareader.models.enums.Status;

import lombok.AllArgsConstructor;

@AllArgsConstructor

public class UnionLeitorGetter implements MangaGetter{
    
    private String url;

    @Override
    public MangaDTO getFromUrl() {
        
        try{
            Document document = Jsoup.connect(url).get();
           
            MangaDTO dto = new MangaDTO();

            Integer count = 1;

            //-------------------------TITLE-------------------------
            String title = document.select("body > div.container > div:nth-child(5) > div.col-md-8.perfil-manga > div:nth-child(" + count + ") > div > h2").text();
            count += 1;


            //-------------------------SCORE-------------------------
            Elements scoreEl = document.select("body > div.container > div:nth-child(5) > div.col-md-8.perfil-manga > div:nth-child(3) > div:nth-child(" + count + ") > h2");
            String scoreText = scoreEl.text();

            String[] scoreSplited = scoreText.split(" ");

            String score = null;
            String scoredBy = null;

            try{
                score = scoreSplited[0].replace("#", "");
                scoredBy = scoreSplited[1];
                count+=1;
            }catch(IndexOutOfBoundsException e){
            }

            //-------------------------GENRE-------------------------
            count +=1;
            Elements genreEl = document.select("body > div.container > div:nth-child(5) > div.col-md-8.perfil-manga > div:nth-child(3) > div:nth-child(" + count + ") > h4");
           
            Elements genreEls = genreEl.get(0).getElementsByTag("a");

            for(int i = 0; i < genreEls.size(); i ++){

                Element genre = genreEls.get(i);

                String description = genre.text();
                String url = genre.attr("href");

                dto.getGenres().add(new GenreDTO(description, url));

            }

            if(dto.getGenres().size() > 0){
                count += 1;
            }


            

            //-------------------------AUTHOR-------------------------
            Elements authorEl = document.select("body > div.container > div:nth-child(5) > div.col-md-8.perfil-manga > div:nth-child(3) > div:nth-child(" + count + ") > h4");
            String author = authorEl.text();
            author = author.substring(author.indexOf(": ") + 2);

            if(!author.equals("")){
                count += 1;
            }

            //-------------------------ARTIST-------------------------
            Elements artistEl = document.select("body > div.container > div:nth-child(5) > div.col-md-8.perfil-manga > div:nth-child(3) > div:nth-child(" + count + ") > h4");
            String artist = artistEl.text();
            artist = artist.substring(artist.indexOf(": ") + 2);

            if(!artist.equals("")){
                count += 1;
            }

            //-------------------------STATUS-------------------------
            Elements statusEl = document.select("body > div.container > div:nth-child(5) > div.col-md-8.perfil-manga > div:nth-child(3) > div:nth-child(" + count + ") > h4");

            String status = statusEl.get(0).getElementsByClass("label").text();

            Status statusEnum = (status.toLowerCase().equals("Ativo".toLowerCase())) ? Status.ATIVO : Status.COMPLETO;
            

            //-------------------------SYNOPSIS-------------------------
            String synopsis = document.select("body > div.container > div:nth-child(5) > div.col-md-8.perfil-manga > div:nth-child(3) > div:nth-child(9) > div > div").text();
           
           
            //-------------------------COVER-------------------------
            String cover = document.select("body > div.container > div:nth-child(5) > div.col-md-8.perfil-manga > div:nth-child(3) > div.col-md-4.col-xs-12.text-center.col-md-perfil > img").attr("src");
            

            //-------------------------CHAPTERS-------------------------
            Elements chaptersEls = document.getElementsByClass("capitulos");

            for(int i = 0; i < chaptersEls.size(); i ++){
                Element el = chaptersEls.get(i);
  
                Element a = el.getElementsByTag("a").get(0);
                Element span = el.getElementsByTag("span").get(1);

                String descrption = a.text() + " " + span.text();
                String chapterUrl = a.attr("href");
                String chapterAppUrl = "/reader?url=" + chapterUrl;
                String chapterApiDownloadUrl = MangareaderApplication.API_CHAPTER_DOWNLOAD_URL_PREFIX + chapterUrl;

                dto.getChapters().add(new ChapterDTO(chapterUrl, descrption, chapterAppUrl, chapterApiDownloadUrl));                
            }



            dto.setStatus(new StatusDTO(null, statusEnum));
            dto.setAuthor(new AuthorDTO(author, null));
            dto.setArtist(new AuthorDTO(artist, null));
            dto.setScore(score);
            dto.setScoredBy(scoredBy);
            dto.setApiUrl(MangareaderApplication.API_MANGA_URL_PREFIX + url);
            dto.setUrl(url);
            dto.setCover(cover);
            dto.setTitle(title);
            dto.setSynopsis(synopsis);

            return dto;
        }catch(IOException | NullPointerException e){
            throw new SourceException("Erro ao tentar recuperar o manga de " + url);
        }
     
    }
    
}
