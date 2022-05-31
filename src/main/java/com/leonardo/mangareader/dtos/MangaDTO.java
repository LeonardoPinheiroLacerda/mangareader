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

public class MangaDTO {
    
    private String title;
    private String cover;
    private String url;
    private String apiUrl;
    private String synopsis;

    private List<GenreDTO> genres = new ArrayList<>();

    private List<ChapterDTO> chapters = new ArrayList<>();

}
