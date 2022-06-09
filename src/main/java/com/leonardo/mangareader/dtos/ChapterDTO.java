package com.leonardo.mangareader.dtos;

import com.leonardo.mangareader.models.enums.ReadStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class ChapterDTO {
    
    private String url;
    private String description;
    private ReadStatus status;

}
