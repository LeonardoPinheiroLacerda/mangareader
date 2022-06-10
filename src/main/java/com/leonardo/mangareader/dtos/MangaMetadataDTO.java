package com.leonardo.mangareader.dtos;

import java.util.ArrayList;
import java.util.List;

import com.leonardo.mangareader.models.enums.Status;

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

public class MangaMetadataDTO {
    
    private String title;
    private String cover;
    private String url;
    private String synopsis;

    private AuthorDTO author;
    private ArtistDTO artist;

    private Status status;
    private Float score;
    private Integer scoredBy;

    private List<GenreDTO> genres = new ArrayList<>();

}
