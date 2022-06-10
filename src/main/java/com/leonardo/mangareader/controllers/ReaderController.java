package com.leonardo.mangareader.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.leonardo.mangareader.dtos.DetailedChapterDTO;
import com.leonardo.mangareader.services.ChapterService;
import com.leonardo.mangareader.services.HistoryService;

import lombok.AllArgsConstructor;

@AllArgsConstructor

@Controller
@RequestMapping("/reader")
public class ReaderController {
 
    private final ChapterService chapterService;
    private final HistoryService historyService;

    @GetMapping
    public ModelAndView index(@RequestParam String url){
        DetailedChapterDTO chapter = chapterService.getFromUrl(url);

        ModelAndView modelAndView = new ModelAndView("screens/reader");
        modelAndView.addObject("chapter",chapter);

        historyService.doHistory(chapter);




        return modelAndView;
    }

}
