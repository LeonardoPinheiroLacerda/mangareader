package com.leonardo.mangareader.dtos;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@ToString

public class DetailedChapterDTO {
    
    public String chapterUrl;
    public String currentChapter;
    public String mangaUrl;
    public String apiDownloadUrl;
    public String title;
    public String apiPreviousUrl;
    public String apiNextUrl;
    public List<String> pages = new ArrayList<>();

}
