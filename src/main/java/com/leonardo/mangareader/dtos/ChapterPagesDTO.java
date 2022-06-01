package com.leonardo.mangareader.dtos;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class ChapterPagesDTO {
    
    public String chapterUrl;
    public String mangaUrl;
    public String apiDownloadUrl;
    public String title;
    public List<String> pages = new ArrayList<>();;

}
