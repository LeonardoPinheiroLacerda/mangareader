package com.leonardo.mangareader.webscraping.chapterGetter;

import com.leonardo.mangareader.dtos.DetailedChapterDTO;

public interface ChapterGetter {
    
    public DetailedChapterDTO getFromUrl();

}
