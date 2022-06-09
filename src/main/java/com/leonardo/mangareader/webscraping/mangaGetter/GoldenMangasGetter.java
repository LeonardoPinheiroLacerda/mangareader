package com.leonardo.mangareader.webscraping.mangaGetter;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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

public class GoldenMangasGetter implements MangaGetter{

    private static final String URL_PREFIX = "https://goldenmangas.top";

    private String url;

    @Override
    public Manga getFromUrl() {

        try{
            Document document = Jsoup.connect(url).get();
           
            Manga manga = new Manga();

            Integer count = 1;

            //-------------------------TITLE-------------------------
            Elements titleEl = document.select("body > article > div.container.manga > div.row > div.col-sm-8 > div.row > div.col-sm-8 > h2:nth-child(" + count + ")");
            String title = titleEl.text();

            if(!title.equals("")){
                count+=1;
            }


            //-------------------------SCORE-------------------------
            Elements scoreEl = document.select("body > article > div.container.manga > div.row > div.col-sm-8 > div.row > div.col-sm-8 > h2:nth-child(" + count + ")");

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
            Elements genreEl = document.select("body > article > div.container.manga > div.row > div.col-sm-8 > div.row > div.col-sm-8 > h5:nth-child(" + count + ")");
            Elements genresEls = genreEl.get(0).children();

            for(int i = 3; i <= genresEls.size(); i ++){

                Elements genre = document.select("body > article > div.container.manga > div.row > div.col-sm-8 > div.row > div.col-sm-8 > h5:nth-child(" + count + ") > a:nth-child(" + i + ")");
                
                String description = genre.text();
                String genreUrl = genre.attr("href");

                genreUrl = genreUrl.replace("..", URL_PREFIX);

                manga.getGenres().add(new Genre(null, description, genreUrl, null));
            }

            if(manga.getGenres().size() > 0){
                count+=1;    
            }


            //-------------------------AUTHOR-------------------------
            Elements authorEl = document.select("body > article > div.container.manga > div.row > div.col-sm-8 > div.row > div.col-sm-8 > h5:nth-child(" + count + ") > a");

            authorEl = document.select("body > article > div.container.manga > div.row > div.col-sm-8 > div.row > div.col-sm-8 > h5:nth-child(" + count + ") > a");
            String authorName = authorEl.text();
            String authorHref = URL_PREFIX + authorEl.attr("href");        
            
            if(!authorHref.equals("")){
                count+=1;
            }
            
            
            //-------------------------ARTIST-------------------------
            Elements artistEl = document.select("body > article > div.container.manga > div.row > div.col-sm-8 > div.row > div.col-sm-8 > h5:nth-child(" + count + ") > a");

            artistEl = document.select("body > article > div.container.manga > div.row > div.col-sm-8 > div.row > div.col-sm-8 > h5:nth-child( " + count + " ) > a");
            String artistName = artistEl.text();
            String artistHref = URL_PREFIX + artistEl.attr("href");
            
            if(!artistHref.equals("")){
                count+=1;
            }


            //-------------------------STATUS-------------------------
            Elements statusEl = document.select("body > article > div.container.manga > div.row > div.col-sm-8 > div.row > div.col-sm-8 > h5:nth-child(" + count + ") > a");

            String status = statusEl.text();

            Status statusEnum = null;
           
            if(status.toLowerCase().equals("Ativo".toLowerCase())){
                statusEnum = Status.ATIVO;
            }else if(status.toLowerCase().equals("Completo".toLowerCase())){
                statusEnum = Status.COMPLETO;
            }


            //-------------------------COVER-------------------------
            String cover = document.select("body > article > div.container.manga > div.row > div.col-sm-8 > div.row > div.col-sm-4.text-right > img").attr("src");


            //-------------------------SYNOPSIS-------------------------
            String synopsis = document.select("#manga_capitulo_descricao").text();


            //-------------------------CHAPTERS-------------------------
            Elements chaptersEls = document.select("#capitulos").first().children();

            for(int i = chaptersEls.size(); i > 0 ; i --){
                
                String chapterUrl = URL_PREFIX + document.select("#capitulos > li:nth-child(" + i + ") > a").attr("href");                
                String description = document.select("#capitulos > li:nth-child(" + i + ") > a > div.col-sm-5").text();

                manga.getChapters().add(new Chapter(null, chapterUrl, description, null, manga, null, null, ReadStatus.NONE));
            }

            
            manga.setTitle(title);
            manga.setCover(URL_PREFIX + cover);
            manga.setUrl(url);
            manga.setSynopsis(synopsis);

            manga.setScore(Float.parseFloat(score));
            manga.setScoredBy(Integer.parseInt(scoredBy));

            manga.setArtist(new Artist(null, artistName, artistHref));
            manga.setAuthor(new Author(null, authorName, authorHref));

            manga.setStatus(statusEnum);

            return manga;
        }catch(IOException | NullPointerException e){
            throw new SourceException("Erro ao tentar recuperar o manga de " + url);
        }
        
    }
    
}
