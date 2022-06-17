package com.leonardo.mangareader.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.leonardo.mangareader.dtos.DetailedChapterDTO;
import com.leonardo.mangareader.dtos.MangaMetadataDTO;
import com.leonardo.mangareader.services.ChapterService;
import com.leonardo.mangareader.services.HistoryService;
import com.leonardo.mangareader.services.MangaService;

import lombok.AllArgsConstructor;

@AllArgsConstructor

@Controller
@RequestMapping("/reader")
public class ReaderController {
 
    private final ChapterService chapterService;
    private final HistoryService historyService;
    private final MangaService MangaService;

    @GetMapping
    public ModelAndView index(@RequestParam String url){
        DetailedChapterDTO chapter = chapterService.getFromUrl(url);
        MangaMetadataDTO manga = MangaService.getMetadataFromUrl(chapter.getMangaUrl());

        ModelAndView modelAndView = new ModelAndView("screens/reader");
        modelAndView.addObject("chapter", chapter);
        modelAndView.addObject("manga", manga);

        historyService.doHistory(chapter);

        return modelAndView;
    }

}
