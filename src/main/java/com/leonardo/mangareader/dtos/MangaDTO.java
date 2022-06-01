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

public class MangaDTO {
    
    private String title;
    private String cover;
    private String url;
    private String apiUrl;
    private String synopsis;

    private AuthorDTO author;
    private AuthorDTO artist;

    private StatusDTO status;
    private String score;
    private String scoredBy;

    private List<GenreDTO> genres = new ArrayList<>();

    private List<ChapterDTO> chapters = new ArrayList<>();

}
