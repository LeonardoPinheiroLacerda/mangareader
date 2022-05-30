package com.leonardo.mangareader.services;

import com.leonardo.mangareader.dtos.ChapterPagesDTO;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor

@Service
public class ChapterService {

    private final ChapterGetterFactoryService factoryService;

    public ChapterPagesDTO getFromUrl(String url) {
        return factoryService
            .getInstance(url)
            .getFromUrl();
    }
}
