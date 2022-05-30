package com.leonardo.mangareader.webscraping.chapterGetter;

import com.leonardo.mangareader.dtos.ChapterPagesDTO;

import lombok.AllArgsConstructor;

@AllArgsConstructor

public class MangaHostedChapterGetter implements ChapterGetter{

    private String url;

    @Override
    public ChapterPagesDTO getFromUrl() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
