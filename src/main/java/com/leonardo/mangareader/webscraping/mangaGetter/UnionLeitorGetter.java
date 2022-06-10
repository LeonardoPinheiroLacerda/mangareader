package com.leonardo.mangareader.webscraping.mangaGetter;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.leonardo.mangareader.exceptions.SourceException;
import com.leonardo.mangareader.models.Artist;
import com.leonardo.mangareader.models.Author;
import com.leonardo.mangareader.models.Chapter;
import com.leonardo.mangareader.models.Genre;
import com.leonardo.mangareader.models.Manga;
import com.leonardo.mangareader.models.enums.ReadStatus;
import com.leonardo.mangareader.models.enums.Status;

import lombok.AllArgsConstructor;

@AllArgsConstructor

public class UnionLeitorGetter implements MangaGetter{
    
    private String url;

    @Override
    public Manga getFromUrl() {
        
        try{
            Document document = Jsoup.connect(url).get();
           
            Manga manga = new Manga();

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

                manga.getGenres().add(new Genre(null, description, url, null));

            }

            if(manga.getGenres().size() > 0){
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

            for(int i = chaptersEls.size() - 1; i >= 0 ; i --){
                Element el = chaptersEls.get(i);
  
                Element a = el.getElementsByTag("a").get(0);
                Element span = el.getElementsByTag("span").get(1);

                String description = a.text() + " " + span.text();
                String chapterUrl = a.attr("href");   

                manga.getChapters().add(new Chapter(null, chapterUrl, description, manga, ReadStatus.NONE));
            }


            manga.setTitle(title);
            manga.setCover(cover);
            manga.setUrl(url);
            manga.setSynopsis(synopsis);

            manga.setScore(Float.parseFloat(score));
            manga.setScoredBy(Integer.parseInt(scoredBy));

            manga.setArtist(new Artist(null, artist, null));
            manga.setAuthor(new Author(null, author, null));

            manga.setStatus(statusEnum);

            // dto.setApiUrl(MangareaderApplication.API_MANGA_URL_PREFIX + url);

            return manga;
        }catch(IOException | NullPointerException e){
            throw new SourceException("Erro ao tentar recuperar o manga de " + url);
        }
     
    }
    
}
