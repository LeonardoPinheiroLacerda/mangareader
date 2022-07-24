package com.leonardo.mangareader.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.leonardo.mangareader.dtos.ChapterHistoryDTO;
import com.leonardo.mangareader.dtos.MangaHistoryDTO;
import com.leonardo.mangareader.models.MangaHistory;
import com.leonardo.mangareader.services.ChapterHistoryService;
import com.leonardo.mangareader.services.DtoMapperService;
import com.leonardo.mangareader.services.MangaHistoryService;

import lombok.AllArgsConstructor;

@AllArgsConstructor

@Controller
@RequestMapping("/history")
public class HistoryController {
    
    private final MangaHistoryService mangaHistoryService;
    private final ChapterHistoryService chapterHistoryService;
    private final DtoMapperService dtoMapperService;

    private final Integer MANGA_PAGE_SIZE = 12;
    private final Integer CHAPTER_PAGE_SIZE = 10;
    
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

        modelAndView.addObject("mangaPageSize", MANGA_PAGE_SIZE);
        modelAndView.addObject("chapterPageSize", CHAPTER_PAGE_SIZE);
        modelAndView.addObject("mangaHistory", mangaHistory);
        modelAndView.addObject("chapterHistory", chapterHistory);
        modelAndView.addObject("isMangaViewMore", isMangaViewMore);
        modelAndView.addObject("isChapterViewMore", isChapterViewMore);
        
        return modelAndView;
    }

    @GetMapping("/mangas/viewmore")
    public ModelAndView mangasViewmore(Pageable pageable){
        ModelAndView modelAndView = new ModelAndView("screens/historyMangas");

        Page<MangaHistory> page = mangaHistoryService.findPage(pageable);
        List<MangaHistoryDTO> dtos = page
            .getContent()
            .stream()
            .map(history -> dtoMapperService.mangaHistoryToMangaHistoryDTO(history))
            .collect(Collectors.toList());

        
        
        modelAndView.addObject("mangaPageSize", MANGA_PAGE_SIZE);
        modelAndView.addObject("actualPage", page.getPageable().getPageNumber());
        modelAndView.addObject("hasNextPage", page.hasNext());
        modelAndView.addObject("mangaHistory", dtos);

        return modelAndView;
    }

}
