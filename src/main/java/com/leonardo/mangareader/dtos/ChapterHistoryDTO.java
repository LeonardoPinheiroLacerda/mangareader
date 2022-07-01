package com.leonardo.mangareader.dtos;

import com.leonardo.mangareader.models.enums.ReadStatus;

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

public class ChapterHistoryDTO {
    
    private SimpleMangaDTO manga;
    private ChapterDTO chapter;
    private String lastRead;
    private ReadStatus status;

}
