package com.leonardo.mangareader.dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class SimpleMangaDTO {
    private String name;
    private String cover;
    private String url;
    private LocalDateTime lastRead;
    private String apiUrl;
}
