package com.leonardo.mangareader.webscraping.chapterGetter;

import com.leonardo.mangareader.dtos.ChapterPagesDTO;

public interface ChapterGetter {
    
    public ChapterPagesDTO getFromUrl();

}
