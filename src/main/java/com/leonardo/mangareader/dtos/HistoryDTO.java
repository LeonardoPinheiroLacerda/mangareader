package com.leonardo.mangareader.dtos;

import java.time.LocalDateTime;

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

public class HistoryDTO {
    
    private MangaMetadataDTO manga;
    private LocalDateTime lastRead;
    private ChapterDTO lastChapterRead;

}
