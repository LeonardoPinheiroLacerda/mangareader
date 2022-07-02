package com.leonardo.mangareader.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.leonardo.mangareader.dtos.ChapterHistoryDTO;
import com.leonardo.mangareader.dtos.MangaHistoryDTO;
import com.leonardo.mangareader.services.ChapterHistoryService;
import com.leonardo.mangareader.services.MangaHistoryService;

import lombok.AllArgsConstructor;

@AllArgsConstructor

@Controller
@RequestMapping("/history")
public class HistoryController {
    
    private final MangaHistoryService mangaHistoryService;
    private final ChapterHistoryService chapterHistoryService;

    @GetMapping
    public ModelAndView index(){

        final Integer mangaLimit = 8;
        final Integer chapterLimit = 7;

        List<MangaHistoryDTO> mangaHistory = mangaHistoryService.getUserHistory();
        List<ChapterHistoryDTO> chapterHistory = chapterHistoryService.getUserHistory();
        Boolean isMangaViewMore = false;
        Boolean isChapterViewMore = false;

        ModelAndView modelAndView = new ModelAndView("screens/history");

        if(mangaHistory.size() > mangaLimit){
            isMangaViewMore = true;
            mangaHistory = mangaHistory.subList(0, mangaLimit);
        }
        if (chapterHistory.size() > chapterLimit){
            isChapterViewMore = true;
            chapterHistory = chapterHistory.subList(0, chapterLimit);
        }

       
       
        

        modelAndView.addObject("mangaHistory", mangaHistory);
        modelAndView.addObject("chapterHistory", chapterHistory);
        modelAndView.addObject("isMangaViewMore", isMangaViewMore);
        modelAndView.addObject("isChapterViewMore", isChapterViewMore);
        
        return modelAndView;
    }

}
